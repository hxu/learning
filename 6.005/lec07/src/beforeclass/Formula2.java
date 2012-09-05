package beforeclass;
import static beforeclass.Im.*;
@SuppressWarnings("unused")
public class Formula2 {
    // datatype definitions:
    //    Formula = ImList<Clause> 
    //    Clause = ImList<Literal>
    //    Literal = Positive(v:Var) + Negative(v:Var)
    //    Var = String
    
    public static class Formula {
        private final ImList<Clause> clauses;
        public Formula() { clauses = new Empty<Clause>(); }
//      operations:
//      public Formula and(Formula that);
//      public Formula or(Formula that);
//      public Formula not();
//      etc.
    }

    public static class Clause {
        private final ImList<Literal> literals;
        public Clause() { literals = new Empty<Literal>(); }
    }

    public static interface Literal {
    }
    public static class Positive implements Literal {
        private final Var var;
        public Positive(Var var) { this.var = var; }
    }
    public static class Negative implements Literal {
        private final Var var;
        public Negative(Var var) { this.var = var; }
    }

    public static class Var {
        private final String name;
        public Var(String name) { this.name = name; }
    }
    
}
