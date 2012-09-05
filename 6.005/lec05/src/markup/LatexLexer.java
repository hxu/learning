package markup;

public class LatexLexer {
    private final String s;
    private int i;
    // s is the string of Latex that we're parsing,
    // and s[i] is the start of the next token to return, or
    // i == s.length means we're at the end of parsing.
    
    public LatexLexer(String s) {
        this.s = s;
        this.i = 0;
    }
    
    public Token next() throws SyntaxErrorException {
        if (i >= s.length()) {
            return new Token(Type.EOF, "");
        }
        
        switch (s.charAt(i)) {
        case '{':
            ++i;
            return new Token(Type.OPEN_BRACE, "{");
            
        case '}':
            ++i;
            return new Token(Type.CLOSE_BRACE, "}");

        case '\\':
            ++i;
            if (s.startsWith("em", i)) {
                i += 2;
                return new Token(Type.EM, "}");
            } else {
                throw new SyntaxErrorException("syntax error at " + s.substring(i));
            }
 
        default:
            // it's a Text token.  Find where it ends.
            int start = i;
            while (i < s.length() 
                   && "{}\\".indexOf(s.charAt(i)) != -1) {
                ++i;
            }
            int end = i;
            return new Token(Type.TEXT, s.substring(start, end));
        }
    }
}
