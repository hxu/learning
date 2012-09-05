package beforeclass;
@SuppressWarnings("unused")
public class Formula1 {
    // datatype definition:
    //    Formula = Var(name:String) 
    //              + Not(f:Formula)
    //              + And(left:Formula, right:Formula)
    //              + Or(left:Formula, right:Formula)
        
    public static interface Formula {
//       operations:
//        public Formula and(Formula that);
//        public Formula or(Formula that);
//        public Formula not();
//        etc.
    }
    
    public static class Var implements Formula{
        private final String name;
        public Var(String name) { this.name = name; }
    }

    public static class And implements Formula{
        private final Formula left, right;
        public And(Formula left, Formula right) { this.left = left; this.right = right; }
    }

    public static class Or implements Formula{
        private final Formula left, right;
        public Or(Formula left, Formula right) { this.left = left; this.right = right; }
    }

    public static class Not implements Formula{
        private final Formula f;
        public Not(Formula f) { this.f = f; }
    }

    
}
