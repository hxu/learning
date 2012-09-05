package beforeclass;
import static beforeclass.Im.*;
@SuppressWarnings("unused")
public class Markup {
    // datatype definitions:
    //    Html = ImList<Node>
    //    Node = Normal(content:String) + Italic(content:String)
    
    public static class Html {
        private final ImList<Node> nodes;
        public Html(ImList<Node> nodes) { this.nodes = nodes; }
    }
    
    public static interface Node {
    }
    
    public static class Normal {
        private final String content;
        public Normal(String content) { this.content = content; }
    }

    public static class Italic {
        private final Html content;
        public Italic(Html content) { this.content = content; }
    }
    
}
