package markup;

/**
 * Token type for a markup language.
 * 
 * A better design would NOT mix the three 
 * languages into one token set like this.
 */
public enum Type {
    // for all three languages
    EOF,    // end of file
    TEXT,   // text that doesn't contain any markup codes
    
    // for markdown only
    UNDERLINE,         // _
    
    // for HTML only
    START_I, // <i>
    END_I,   // </i>
    
    // for Latex only
    OPEN_BRACE,      // {
    EM,              // \em
    CLOSE_BRACE,     // }
    
}
