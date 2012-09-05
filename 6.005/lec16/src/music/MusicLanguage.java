package music;

import static music.Pitch.OCTAVE;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * MusicLanguage defines static methods for
 * constructing and manipulating Music expressions, 
 * particularly to create
 * recursive music like rounds, canons, and fugues.
 */
public class MusicLanguage {
    // Prevent instantiation
    private MusicLanguage() {}
    
    ////////////////////////////////////////////////////
    // Factory methods
    ////////////////////////////////////////////////////
    
    /**
     * Make Music from a string using a variant of abc notation
     *  (see http://www.walshaw.plus.com/abc/examples/).
     * The notation consists of whitespace-delimited symbols representing either
     * notes or rests. The vertical bar | may be used as a delimiter 
     * for measures; make() treats it as a space.
     * Grammar:
     * notes ::= symbol*
     * symbol :: = . duration     for a rest
     *             pitch duration for a note
     *      pitch :: = accidental letter octave*
     *              accidental ::= empty string for natural,
     *                                    _ for flat,
     *                                    ^ for sharp
     *              letter ::= one of A-G                      
     *              octave ::= ' to raise one octave
     *                         , to lower one octave
     *      duration ::= empty string for one-beat duration,
     *                   /n for 1/n beat,
     *                   n for n-beat duration,
     *                   n/m for n/m-beat duration
     * Examples (assuming 4/4 common time, i.e. 4 beats per measure):
     *     C     quarter note, middle C
     *     A'2   half note, high A  
     *     _D/2  eighth note, middle D flat
     * 
     * @param notes string of notes and rests in simplified abc notation given above
     * @param instr instrument to play the notes with
     */
    public static Music notes(String notes, Instrument instr) {
        Music m = new Rest(0);
        for (String sym : notes.split("[\\s|]+")) {
            if (!sym.isEmpty()) {
                m = concat(m, parseSymbol(sym, instr));
            }
        }
        return m;
    }
    
    // Parse a symbol into a Note or a Rest.
    private static Music parseSymbol(String symbol, Instrument instr) {
        Matcher m = Pattern.compile("([^/0-9]*)([0-9]+)?(/[0-9]+)?").matcher(symbol);
        if (!m.matches()) throw new IllegalArgumentException("couldn't understand " + symbol);

        String pitchSymbol = m.group(1);

        double duration = 1.0;
        if (m.group(2) != null) duration *= Integer.valueOf(m.group(2));
        if (m.group(3) != null) duration /= Integer.valueOf(m.group(3).substring(1));

        if (pitchSymbol.equals(".")) return new Rest(duration);
        else return new Note(duration, parsePitch(pitchSymbol), instr);
    }
    
    // Parse a symbol into a Pitch.
    private static Pitch parsePitch(String symbol) {
        if (symbol.endsWith("'")) return parsePitch(symbol.substring(0, symbol.length()-1)).transpose(OCTAVE);
        else if (symbol.endsWith(",")) return parsePitch(symbol.substring(0, symbol.length()-1)).transpose(-OCTAVE);
        else if (symbol.startsWith("^")) return parsePitch(symbol.substring(1)).transpose(1);
        else if (symbol.startsWith("_")) return parsePitch(symbol.substring(1)).transpose(-1);
        else if (symbol.length() != 1) throw new IllegalArgumentException("can't understand " + symbol);
        else return new Pitch(symbol.charAt(0));
    }
    
    
    /**
     * @param duration length of rest, must be >= 0
     * @return rest of duration beats
     */
    public static Music rest(double duration) {
        return new Rest(duration);
    }

    
    ////////////////////////////////////////////////////
    // Producers
    ////////////////////////////////////////////////////

    /**
     * @param m1 first piece of music
     * @param m2 second piece of music
     * @return m1 followed by m2
     */
    public static Music concat(Music m1, Music m2) {
        return new Concat(m1, m2);
    }
        
    /**
     * @param m1 first piece of music
     * @param m2 second piece of music
     * @return m1 played at the same time as m2
     */
    public static Music together(Music m1, Music m2) {
        return new Together(m1, m2);
    }
    
    /**
     * @param m music to loop forever
     * @return music that repeatedly plays m in an endless loop
     */
    public static Music forever(Music m) {
        return new Forever(m);
    }
        

    
    ////////////////////////////////////////////////////
    // More operations on Music
    ////////////////////////////////////////////////////
    
    /**
     * param m piece of music
     * @returns set of instruments used by m
     */
    public static Set<Instrument> instrumentsUsed(Music m) {
        final Set<Instrument> instruments = new HashSet<Instrument>();        
        m.accept(new VoidVisitor () {
            public Void on(Note m) {
                instruments.add(m.instrument());
                return null;
            }
        });
        return instruments;
    }
    
    /**
     * Pause before playing some music.
     * @param m piece of music
     * @param beats length of delay in beats, must be >= 0
     * @returns rest of duration beats followed by m
     */
    public static Music delay(Music m, double beats) {
        return concat(new Rest(beats), m);
    }
    
    /**
     * Speed up or slow down a piece of music by a factor of speedup.
     * For example, changeTempo(m,2) returns music that plays twice as fast as m.
     * @param m piece of music
     * @param speedup factor to increase speed of music, must be > 0
     * @returns m' such that m'.duration() = m.duration()/speedup
     */
    public static Music changeTempo(Music m, final double speedup) {
        return m.accept(new IdentityVisitor() {
            @Override
            public Music on(Note m) {
                return new Note(m.duration()/speedup, m.pitch(), m.instrument());
            }
            @Override
            public Music on(Rest m) {
                return new Rest(m.duration()/speedup);
            }
        });
    }
    
    /**
     * Change all the notes in a piece of music to use a single instrument. 
     * For example, changeInstrument(m,PIANO) returns music that is played
     * entirely by the piano.
     * @requires m, instr != null
     * @returns m' such that for all notes n in m', n.instrument() == instr,
     *   but otherwise m' is identical to m 
     */
    public static Music changeInstrument(Music m, final Instrument instr) {
        return m.accept(new IdentityVisitor() {
            @Override
            public Music on(Note m) {
                return new Note(m.duration(), m.pitch(), instr);
            }
        });
    }
    
    /**
     * Replace all notes that use oldInstr with notes that use newInstr instead. 
     * @requires m, oldInstr, newInstr != null
     * @returns m' such that for all notes n in m such that n.instrument() == newInstr,
     *    the corresponding note n' in m' has n'.instrument() == newInstr.
     *   Otherwise m' is identical to m 
     */
    public static Music replaceInstrument(Music m, final Instrument oldInstr, final Instrument newInstr) {
        return m.accept(new IdentityVisitor() {
            @Override
            public Music on(Note m) {
                if (m.instrument().equals(oldInstr)) return new Note(m.duration(), m.pitch(), newInstr);
                else return m;
            }
        });
    }

    /**
     * Transpose all notes upward or downward in pitch.
     * @requires m != null
     * @returns m' such that for all notes n in m, the corresponding note n' in m'
     *  has n'.pitch() == n.pitch().transpose(semitonesUp).  Otherwise m' is identical
     *  to m.
     */
    public static Music transpose(Music m, final int semitonesUp) {
        return m.accept(new IdentityVisitor() {
            @Override
            public Music on(Note m) {
                return new Note(m.duration(), m.pitch().transpose(semitonesUp), m.instrument());
            }
        });
    }
    
}
