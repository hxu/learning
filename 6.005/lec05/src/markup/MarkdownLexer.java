package markup;

/**
 * MarkdownLexer is a mutable object that
 * generates a stream of tokens from a string in
 * markdown format.
 */
public class MarkdownLexer {
    private final String s;
    private int i;
    // s is the string of markdown text that we're parsing,
    // and s[i] is the start of the next token to return, or
    // i == s.length means we're at the end of parsing.
    
    /**
     * Make a MarkdownLexer.
     * @param s  string to convert into markdown tokens
     */
    public MarkdownLexer(String s) {
        this.s = s;
    }
    
    /**
     * Modifies this object by consuming a token 
     * from the input stream.
     * @return next token on the stream, or EOF token
     *   if there are no more tokens in the stream.
     */
    public Token next() {
        if (i >= s.length()) {
            return new Token(Type.EOF, "");
        }
        
        switch (s.charAt(i)) {
        case '_':
            ++i;
            return new Token(Type.UNDERLINE, "_");

        default:
            // it's a Text token.  Find where it ends.
            int start = i;
            while (i < s.length() && s.charAt(i) != '_') {
                ++i;
            }
            int end = i;
            return new Token(Type.TEXT, s.substring(start, end));
        }
    }
}
