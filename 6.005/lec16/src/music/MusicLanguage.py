from music.MusicLanguage import *
from music.Instrument import *
from music import Pitch
from music.Pitch import OCTAVE
from java.lang.Double import POSITIVE_INFINITY # used for duration of forever()

#
#    ////////////////////////////////////////////////////////////////////
#    // Playing the music with MIDI 
#    ////////////////////////////////////////////////////////////////////
#

from music.midi import MusicPlayer

def play(music):
    ''' play music with the MIDI synthesizer '''
    MusicPlayer().play(music)


#
#    ////////////////////////////////////////////////////////////////////
#    // General higher-order functions 
#    ////////////////////////////////////////////////////////////////////
#

def identity(x):
    '''identity:X->X is the identity function.'''
    return x

def compose(f, g):
    '''f:A->B, g:B->C; returns h:A->C such that h=f o g.'''
    return lambda x: g(f(x))

def repeated(f, n):
    '''f:X->X, n:int >= 0.  Returns f^n, i.e. f composed with itself n times.'''
    if n==0:
        return identity;
    else:
        return compose(repeated(f, n-1), f)

def series(e, binop, f, n):
    '''e:E, binop:ExE->E, f:E->E, n:int >= 1; also requires binop to be associative.
       Returns e binop f(e) binop f^2(e) binop ... binop f^{n-1}(e).'''
    return reduce(binop, reduce(lambda fs,i: fs + [f(fs[-1])], range(1, n), [e]))
    
#
#    ////////////////////////////////////////////////////////////////////
#    // Functional objects that transform Music in interesting ways
#    ////////////////////////////////////////////////////////////////////
#    

def delayer(beats):
    '''beats:int >= 0; returns f:Music->Music that delays music by that number of beats'''
    return lambda music: delay(music, beats)

def tempoChanger(speedup):
    '''speedup:number > 0; returns f:Music->Music that speeds up music by a factor m'''
    return lambda music: changeTempo(music, speedup)

def instrumentChanger(instr):
    '''instr:Instrument; returns f:Music->Music that plays all the music with instr.'''
    return lambda music: changeInstrument(music, instr)

def instrumentReplacer(oldInstr, newInstr):
    '''oldInstr,newInstr:Instrument; returns f:Music->Music that replaces notes played by oldInstr
       with the same note played by newInstr.'''
    return lambda music: replaceInstrument(music, oldInstr, newInstr) 

def instrumentSequence(instrs):
    '''instrs:list(Instrument); returns f:Music->Music that replaces instrs[i] with instrs[i+1].'''
    def swapArgs(f): return lambda a,b: f(b, a)
    return reduce(swapArgs(compose), map(instrumentReplacer, instrs[:-1], instrs[1:]), identity)

def transposer(semitonesUp):
    '''semitonesUp:int; returns f:Music->Music that transposes music upward by semitonesUp.'''
    return lambda music: transpose(music, semitonesUp)

octave_higher = transposer(OCTAVE)

octave_lower = transposer(-OCTAVE)


#    //////////////////////////////////////////////////////////////////
#    // Operations for multiple voices: rounds, canons, counterpoint
#    //////////////////////////////////////////////////////////////////

def counterpoint(music, voices, f):
    '''music:Music, f:Music->Music, voice:int >= 1
       Returns n-voice contrapuntal composition 
       in which each voice i is given by f^i(m).'''
    return series(music, together, f, voices)

def canon(music, voices, beats, f=identity):
    '''music:Music, beats:int >= 0, f:Music->Music, voices:int >= 1
       Returns n-voice canon in which each voice i is given by f^i(m), 
       entering after i*beats.'''
    return counterpoint(music, voices, compose(f, delayer(beats)))


#
#    //////////////////////////////////////////////////////////////////
#    // Operations for repeating
#    //////////////////////////////////////////////////////////////////
#

def repeat(music, n, f=identity):
    '''music:Music, n:int >= 1, f:Music->Music.
       Returns n repetitions of music, where the ith repetition is f^{i-1}(music)'''
    return series(music, concat, f, n)

def accompany(music1, music2):
    '''music1,music2:Music.
       Requires music1 or music2 to run forever, or one's duration to be an even multiple
       of the other's duration.
       Returns a piece of music that plays music1 and music2 simultaneously,
       ending at the same time as well.'''
    if music1.duration() < music2.duration():
        return accompany(music2, music1)
    # so now music1.duration >= music2.duration
    elif music2.duration() == POSITIVE_INFINITY:
        return together(music1, music2)
    elif music1.duration() == POSITIVE_INFINITY:
        return together(music1, forever(music2))
    else:
        return together(music1, repeat(music2, round(m1.duration() / m2.duration())))
    

#    //////////////////////////////////////////////////////////////////
#    // Examples
#    //////////////////////////////////////////////////////////////////

# Row Row Row Your Boat
rowYourBoat = notes("""
   C C C3/4 D/4 E |
   E3/4 D/4 E3/4 F/4 G2 |
   C'/3 C'/3 C'/3 G/3 G/3 G/3 E/3 E/3 E/3 C/3 C/3 C/3 |
   G3/4 F/4 E3/4 D/4 C2
""", PIANO)        

# play it and then play it again, an octave higher
rowTwice = concat(rowYourBoat, transpose(rowYourBoat, OCTAVE))

# play it as a 4-voice round, each voice coming in after 4 beats
rowRound = canon(rowYourBoat, voices=4, beats=4)

# same 4-voice canon, but each voice an octave higher
rowOctaves = canon(rowYourBoat, voices=4, beats=4, f=transposer(OCTAVE))

# same thing, but repeated forever
rowForever = canon(forever(rowYourBoat), voices=4, beats=4, f=octave_higher);


# Frere Jacques
frereJacques = notes("""
    F G A F | F G A F | 
    A _B C'2 | A _B C'2 |
    C'/2 D'/2 C'/2 _B/2 A F |
    C'/2 D'/2 C'/2 _B/2 A F |
    F C F2 | F C F2
""", PIANO)
        
# 4-voice canon, come in after two measures, using four different instruments
frereRound = canon(frereJacques, voices=4, beats=frereJacques.duration()/4, f=instrumentSequence([PIANO, TRUMPET, ACCORDION, CHOIR_AAHS]))



# Pachelbel's canon
# The melody line below isn't complete -- for the rest, see http://www.musicaviva.com/ensemble/canon/music.tpl?filnavn=pachelbel-canon-3mndbc
pachelbelMelody = notes("""
    ^F'2 E'2 | D'2 ^C'2 | B2 A2 | B2 ^C'2 |
    D'2 ^C'2 | B2 A2 | G2 ^F2 | G2 E2 |
    D ^F A G | ^F D ^F E | D B, D A | G B A G |
    ^F D E ^C' | D' ^F' A' A | B G A ^F | D D' D3/2 .1/2 |
""", VIOLIN)
pachelbelCanon = canon(forever(pachelbelMelody), voices=3, beats=16)

# add a bass line, which starts by itself and then accompanies the melody
pachelbelBass = notes("D,2 A,,2 | B,,2 ^F,,2 | G,,2 D,,2 | G,,2 A,,2", CELLO);
pachelbel = concat(pachelbelBass, accompany(pachelbelCanon, pachelbelBass))


