package beforeclass;

public class Formula3_instanceof {
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

    public static boolean hasNot(Formula f) {
        // bad idea, not statically checked!
        if (f instanceof Var) {
            return false;
        } else if (f instanceof Not) {
            return true;
        } else if (f instanceof And) {
            And a = (And) f;
            return hasNot(a.left) || hasNot(a.right);
        } else if (f instanceof Or) {
            Or o = (Or) f;
            return hasNot(o.left) || hasNot(o.right);
        } else {
            throw new AssertionError("shouldn't get here!");
        }
    }
    
}
