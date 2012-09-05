package beforeclass;

public class Formula5_visitor_pattern {
    // datatype definition:
    //    Formula = Var(name:String) 
    //              + Not(f:Formula)
    //              + And(left:Formula, right:Formula)
    //              + Or(left:Formula, right:Formula)
        
    public static interface Formula {
        // the method that invokes a visitor object
        public <R> R accept(Visitor<R> v);        
    }
    
    public static class Var implements Formula{
        public final String name;
        public Var(String name) { this.name = name; }
        public <R> R accept(Visitor<R> v) { return v.onVar(this); }
    }

    public static class Not implements Formula{
        public final Formula f;
        public Not(Formula f) { this.f = f; }
        public <R> R accept(Visitor<R> v) { return v.onNot(this); }
    }

    public static class And implements Formula{
        public final Formula left, right;
        public And(Formula left, Formula right) { this.left = left; this.right = right; }
        public <R> R accept(Visitor<R> v) { return v.onAnd(this); }
    }

    public static class Or implements Formula{
        public final Formula left, right;
        public Or(Formula left, Formula right) { this.left = left; this.right = right; }
        public <R> R accept(Visitor<R> v) { return v.onOr(this); }        
    }
    
    // the interface that all visitor classes implement
    public interface Visitor<R> {
        public R onVar(Var v);
        public R onNot(Not n);
        public R onAnd(And a);
        public R onOr(Or o);
    }
    
    
    // the hasNot function
    public static boolean hasNot(Formula f) {
        return f.accept(new HasNot());
    }

    // the visitor class that has the cases of the function
    static class HasNot implements Visitor<Boolean> {
        public Boolean onVar(Var v) { return false; }
        public Boolean onNot(Not n) { return true; }
        public Boolean onAnd(And a) { return hasNot(a.left) || hasNot(a.right); }
        public Boolean onOr(Or o) { return hasNot(o.left) || hasNot(o.right); }
    }
    
}
