package beforeclass;


public class Formula1_datatype {
    // datatype definition:
    //    Formula = Var(name:String) 
    //              + Not(f:Formula)
    //              + And(left:Formula, right:Formula)
    //              + Or(left:Formula, right:Formula)
        
    public static interface Formula {
    }
    
    public static class Var implements Formula{
        public final String name;
        public Var(String name) { this.name = name; }
    }

    public static class Not implements Formula{
        public final Formula f;
        public Not(Formula f) { this.f = f; }
    }

    public static class And implements Formula{
        public final Formula left, right;
        public And(Formula left, Formula right) { this.left = left; this.right = right; }
    }

    public static class Or implements Formula{
        public final Formula left, right;
        public Or(Formula left, Formula right) { this.left = left; this.right = right; }
    }
    
}
