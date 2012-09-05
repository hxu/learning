package markup;

import static org.junit.Assert.*;

import org.junit.Test;

public class MarkdownLexerTest {

    @Test
    public void test() {
        MarkdownLexer lex = new MarkdownLexer("this is _italic_ text");
        Token tok;
        
        tok = lex.next();
        assertEquals(tok.getType(), Type.TEXT);
        assertEquals(tok.getValue(), "this is ");

        tok = lex.next();
        assertEquals(tok.getType(), Type.UNDERLINE);
        assertEquals(tok.getValue(), "_");

        tok = lex.next();
        assertEquals(tok.getType(), Type.TEXT);
        assertEquals(tok.getValue(), "italic");

        tok = lex.next();
        assertEquals(tok.getType(), Type.UNDERLINE);
        assertEquals(tok.getValue(), "_");

        tok = lex.next();
        assertEquals(tok.getType(), Type.TEXT);
        assertEquals(tok.getValue(), " text");

        tok = lex.next();
        assertEquals(tok.getType(), Type.EOF);
    }

}
