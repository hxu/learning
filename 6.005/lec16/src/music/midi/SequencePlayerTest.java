package music.midi;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

import music.Instrument;

import org.junit.Test;
public class SequencePlayerTest {
    
    @Test public void playPiece1() throws MidiUnavailableException, InvalidMidiDataException {
        // create a new player, with 120 beats (i.e. quarter note) per
        // minute, with 12 ticks per quarter note
        SequencePlayer player = new SequencePlayer(120, 12);

        player.addNote(Instrument.PIANO, 60, 0, 12);
        player.addNote(Instrument.VIOLIN, 60, 12, 12);
        player.addNote(Instrument.PIANO, 60, 24, 9);
        player.addNote(Instrument.VIOLIN, 62, 33, 3);
        player.addNote(Instrument.VIOLIN, 64, 36, 12);
        
        player.play();
    }
        
        
}
