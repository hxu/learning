package markup;

/**
 * A Token is an immutable value representing a token in 
 * a markup language (HTML, Latex, or markdown).
 */
public class Token {
    private final Type type;
    private final String value;
    
    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }
    
    public Type getType() {
        return type;
    }
    
    public String getValue() {
        return value;
    }
}
