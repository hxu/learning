package markup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlLexer {
    private final String s;
    private int i = 0;
    private final Matcher matcher;
    
    // Regex matching the next token.
    private static final Pattern TOKEN_REGEX 
        = Pattern.compile (
             "^"  // anchors to the beginning of the match start, so that we don't skip any characters
             + "(<i>)"  // START_I
             + "|"
             + "(</i>)" // END_I
             + "|"
             + "([^<]+)" // TEXT
          );
     
    // The token types for each of the parenthesized patterns in TOKEN_REGEX.
    private static final Type[] TOKEN_TYPES
        = { 
            Type.START_I, 
            Type.END_I, 
            Type.TEXT 
          };
    
    public HtmlLexer(String s) {
        this.s = s;
        this.matcher = TOKEN_REGEX.matcher(s);
    }
        
    public Token next() throws SyntaxErrorException {
        if (i >= s.length()) {
            return new Token(Type.EOF, "");            
        }
        
        // Look for the next token
        if (!matcher.find(i)) {
            // No token found
            throw new SyntaxErrorException("syntax error at " + s.substring(i));
        }
        
        // Get the part of the string that the regex matched,
        // and advance our state
        String value = matcher.group(0);
        i = matcher.end();
        
        // Each set of parentheses in TOKEN_REGEX is a "capturing group", which
        // means that the regex matcher remembers where it matched and returns it
        // with the method group(i), where i=1 is the first set of parens.
        // Only one of the groups can match, so look for a non-null group.
        for (int i = 1; i < matcher.groupCount(); ++i) {
            if (matcher.group(i) != null) {
                // since i is 1-based, use i-1 to find the token type for this pattern
                return new Token(TOKEN_TYPES[i-1], value);
            }
        }
        
        // if we got here, there's a bug in the regex -- Matcher said we matched the 
        // expression, but it didn't match any of the parens
        throw new AssertionError("shouldn't get here");
    }
}
