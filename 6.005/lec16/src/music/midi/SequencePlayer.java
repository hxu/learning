package music.midi;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;

/**
 * Schedules and plays a sequence of notes at given time steps (or "ticks")
 */
public class SequencePlayer {
    private final Synthesizer synthesizer;

    // active MIDI channels, assigned to instruments
    private final Map<music.Instrument, Integer> channels = new HashMap<music.Instrument,Integer>();

    // next available channel number (unassigned to an instrument yet)
    private int nextChannel = 0;
    
    private final Sequencer sequencer;
    private final Track track;
    private final int beatsPerMinute;

    private static int DEFAULT_VELOCITY = 100; // the volume

    private void checkRep() {
        assert sequencer != null : "sequencer should be non-null";
        assert track != null : "track should be non-null";
        assert beatsPerMinute >= 0 : "should be positive number of beats per minute";
    }

    /**
     * @param beatsPerMinute
     *            : the number of beats per minute, where each beat is equal to
     *            a quarter note in duration
     * @param ticksPerQuarterNote
     *            : the number of ticks per quarter note
     * @throws MidiUnavailableException
     * @throws InvalidMidiDataException
     */
    public SequencePlayer(int beatsPerMinute, int ticksPerQuarterNote)
            throws MidiUnavailableException, InvalidMidiDataException {
        synthesizer = MidiSystem.getSynthesizer();
        synthesizer.open();
        synthesizer.loadAllInstruments(synthesizer.getDefaultSoundbank());
        
        this.sequencer = MidiSystem.getSequencer();

        // Create a sequence object with with tempo-based timing, where
        // the resolution of the time step is based on ticks per quarter note
        Sequence sequence = new Sequence(Sequence.PPQ, ticksPerQuarterNote);
        this.beatsPerMinute = beatsPerMinute;

        // Create an empty track. Notes will be added to this track.
        this.track = sequence.createTrack();

        sequencer.setSequence(sequence);

        checkRep();
    }

    /**
     * @param eventType
     * @param note
     * @param tick
     * @pre eventType is a valid MidiMessage type in ShortMessage && note is a
     *      valid pitch value && tick => 0
     */
    private void addMidiEvent(int eventType, int channel, int note, int tick) throws InvalidMidiDataException {
        ShortMessage msg = new ShortMessage();
        msg.setMessage(eventType, channel, note, DEFAULT_VELOCITY);
        MidiEvent event = new MidiEvent(msg, tick);
        this.track.add(event);
    }

    /**
     * @param note
     *            : the pitch value for the note to be played
     * @param startTick
     *            : the starting tick
     * @param numTicks
     *            : the number of ticks for which this note should be played
     * @pre note is a valid pitch value && startTick >= 0 && numTicks >= 0
     * @post schedule the note to be played starting at startTick for the
     *       duration of numTicks
     */
    public void addNote(music.Instrument instr, int note, int startTick, int numTicks) {
        int channel = getChannel(instr);
        try {
            // schedule two events in the track, one for starting a note and
            // the other for ending the note.
            addMidiEvent(ShortMessage.NOTE_ON, channel, note, startTick);
            addMidiEvent(ShortMessage.NOTE_OFF, channel, note, startTick + numTicks);
        } catch (InvalidMidiDataException e) {
            String msg = MessageFormat.format("Cannot add note with the pitch {0} at tick {1} " +
            		"for duration of {2}", note, startTick, numTicks);
            throw new RuntimeException(msg, e);
        }
    }

    /**
     * @post the sequencer is opened to begin playing its track
     */
    public void play() throws MidiUnavailableException {
        sequencer.open();
        sequencer.setTempoInBPM(this.beatsPerMinute);

        // start playing!
        sequencer.start();

        // busy-wait until the sequencer stops playing or the user presses Enter
        while (sequencer.isRunning()) {
            try {
                if (System.in.available() > 0) {
                    break;
                }
            } catch (IOException e) {                
            }
            Thread.yield();
        }

        // stop & close the sequencer
        sequencer.stop();
        sequencer.close();
    }
    
    private int getChannel(music.Instrument instr) {
        // check whether this instrument already has a channel
        if (channels.containsKey(instr)) {
            return channels.get(instr);
        }
        
        int channel = allocateChannel();
        patchInstrumentIntoChannel(channel, instr);            
        channels.put(instr, channel);
        checkRep();
        return channel;
    }

    private int allocateChannel() {
        MidiChannel[] channels = synthesizer.getChannels();
        if (nextChannel >= channels.length) throw new RuntimeException("tried to use too many instruments: limited to " + channels.length);
        return nextChannel++;
    }
    
    private void patchInstrumentIntoChannel(int channel, music.Instrument instr) {
        try {
            addMidiEvent(ShortMessage.PROGRAM_CHANGE, channel, instr.ordinal(), 0);
        } catch (InvalidMidiDataException e) {
            throw new RuntimeException("Cannot set instrument", e);
        }
    }


    /**
     * @post returns a string that displays the entire track information as a
     *       sequence of MIDI events, where each event is either turning on or
     *       off a note at a certain tick
     */
    @Override
    public String toString() {
        String trackInfo = "";
        
        for (int i = 0; i < track.size(); i++) {
            MidiEvent e = track.get(i);
            MidiMessage msg = e.getMessage();
            String msgString = "";

            if (msg instanceof javax.sound.midi.ShortMessage) {
                ShortMessage smg = ((ShortMessage) msg);
                int command = smg.getCommand();
                String commandType = "UnknownCommand";

                // determine the type of the command in this message
                if (command == ShortMessage.NOTE_OFF) {
                    commandType = "NOTE_OFF";
                } else if (command == ShortMessage.NOTE_ON) {
                    commandType = "NOTE_ON ";
                }

                msgString = "Event: " + commandType + " Pitch: " + smg.getData1() + " ";
            } else {
                msgString = "***** End of track *****  ";
            }

            trackInfo = trackInfo + msgString + " Tick: " + e.getTick() + "\n";
        }

        return trackInfo;
    }

    /**
     * play an octave up and back down starting from middle C addNote(base,
     * tick, duration) schedules a note with pitch value 'base' starting at
     * 'tick' to be played for 'duration' number of ticks For example,
     * addNote(new Pitch('C').toMidiNote(), 10, 1) plays the middle C at
     * time step 10 for half the duration of a quarter note.
     */
    public static void main(String[] args) {
        // test
        SequencePlayer player;
        try {

            // create a new player, with 120 beats (i.e. quarter note) per
            // minute, with 2 tick per quarter note
            player = new SequencePlayer(120, 2);
//
//            player.addNote(new Pitch('C').toMidiNote(), 0, 1);
//            player.addNote(new Pitch('D').toMidiNote(), 1, 1);
//            player.addNote(new Pitch('E').toMidiNote(), 2, 1);
//            player.addNote(new Pitch('F').toMidiNote(), 3, 1);
//            player.addNote(new Pitch('G').toMidiNote(), 4, 1);
//            player.addNote(new Pitch('A').toMidiNote(), 5, 1);
//            player.addNote(new Pitch('B').toMidiNote(), 6, 1);
//            player.addNote(new Pitch('C').transpose(Pitch.OCTAVE).toMidiNote(), 7, 1);
//            player.addNote(new Pitch('B').toMidiNote(), 8, 1);
//            player.addNote(new Pitch('A').toMidiNote(), 9, 1);
//            player.addNote(new Pitch('G').toMidiNote(), 10, 1);
//            player.addNote(new Pitch('F').toMidiNote(), 11, 1);
//            player.addNote(new Pitch('E').toMidiNote(), 12, 1);
//            player.addNote(new Pitch('D').toMidiNote(), 13, 1);
//            player.addNote(new Pitch('C').toMidiNote(), 14, 1);

            System.out.println(player);

            // play!
            player.play();

            /*
             * Note: A possible weird behavior of the Java sequencer: Even if the
             * sequencer has finished playing all of the scheduled notes and is
             * manually closed, the program may not terminate. This is likely
             * due to daemon threads that are spawned when the sequencer is
             * opened but keep on running even after the sequencer is killed. In
             * this case, you need to explicitly exit the program with
             * System.exit(0).
             */
            // System.exit(0);

        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        }

    }

}
