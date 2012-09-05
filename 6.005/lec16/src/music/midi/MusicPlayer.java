package music.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import music.Concat;
import music.Forever;
import music.Music;
import music.Note;
import music.Pitch;
import music.Rest;
import music.Together;

/**
 * MusicPlayer can play a Music expression on the computer's 
 * MIDI synthesizer.
 */
public class MusicPlayer {
    private final static int BEATS_PER_MINUTE = 120;
    private final static int TICKS_PER_BEAT = 64;
    private final static int MAXIMUM_PLAYBACK_TICKS = TICKS_PER_BEAT * BEATS_PER_MINUTE * 60 /*minutes*/;
    
    // middle C in the Pitch data type, used to
    // map pitches to Midi frequency numbers.
    private final static Pitch C = new Pitch('C');
    
    /**
     * @effects play the music
     * @throws MidiUnavailableException if no MIDI device is available to play on
     */
    public void play(Music m) throws MidiUnavailableException, InvalidMidiDataException {
        SequencePlayer player = new SequencePlayer(BEATS_PER_MINUTE, TICKS_PER_BEAT);
        
        // load the player with a sequence created from music
        Music.Visitor<Void> v = new PlayVisitor(player);
        m.accept(v);
        
        // start playing
        player.play();
    }

    private static class PlayVisitor implements Music.Visitor<Void> {
        private final SequencePlayer player;
        private int ticksElapsed; // number of ticks that this visitor has played
        
        public PlayVisitor(SequencePlayer player) {
            this.player = player;
        }
        
        public Void on(Concat m) {
            m.first().accept(this);
            m.second().accept(this);
            return null;
        }
        
        public Void on(Forever m) {
            if (m.duration() != 0) {
                while (ticksElapsed < MAXIMUM_PLAYBACK_TICKS) {
                    m.loop().accept(this);
                }
            }
            return null;
        }

        public Void on(Note m) {
            int ticks = (int) (m.duration() * TICKS_PER_BEAT);
            player.addNote(m.instrument(),
                          m.pitch().difference(C) + 60,
                          ticksElapsed, 
                          ticks);
            ticksElapsed += ticks;
            return null;
        }

        public Void on(Rest m) {
            ticksElapsed += (int) (m.duration() * TICKS_PER_BEAT);
            return null;
        }

        public Void on(final Together m) {
            PlayVisitor v = new PlayVisitor(player);
            m.top().accept(v);
            m.bottom().accept(this);
            ticksElapsed = Math.max(ticksElapsed, v.ticksElapsed);
            return null;
        }        
    }
}
