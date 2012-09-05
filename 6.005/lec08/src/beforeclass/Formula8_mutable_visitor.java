package beforeclass;

import java.util.HashSet;
import java.util.Set;

public class Formula8_mutable_visitor {
    // datatype definition:
    //    Formula = Var(name:String) 
    //              + Not(f:Formula)
    //              + And(left:Formula, right:Formula)
    //              + Or(left:Formula, right:Formula)
        
    public static interface Formula {
        public interface Visitor<R> {
            public R on(Var v);
            public R on(Not n);
            public R on(And a);
            public R on(Or o);
        }
        public <R> R accept(Visitor<R> v);        
    }
    
    public static class Var implements Formula{
        public final String name;
        public Var(String name) { this.name = name; }
        public <R> R accept(Visitor<R> v) { return v.on(this); }
    }

    public static class Not implements Formula{
        public final Formula f;
        public Not(Formula f) { this.f = f; }
        public <R> R accept(Visitor<R> v) { return v.on(this); }
    }

    public static class And implements Formula{
        public final Formula left, right;
        public And(Formula left, Formula right) { this.left = left; this.right = right; }
        public <R> R accept(Visitor<R> v) { return v.on(this); }
    }

    public static class Or implements Formula{
        public final Formula left, right;
        public Or(Formula left, Formula right) { this.left = left; this.right = right; }
        public <R> R accept(Visitor<R> v) { return v.on(this); }        
    }
    

    /**
     * @return set of variables used in a formula
     */    
    // maintaining mutable state in a visitor    
    public static Set<String> varSet (Formula f) {
        class VarSet implements Formula.Visitor<Void> {
            public final Set<String> vars = new HashSet<String>();
            public Void on(Var v) { vars.add(v.name); return null; }
            public Void on(Not n) { n.f.accept(this); return null; }
            public Void on(And a) { a.left.accept(this); a.right.accept(this); return null; }
            public Void on(Or o)  { o.left.accept(this); o.right.accept(this); return null; }        
        }
        VarSet vs = new VarSet();
        f.accept(vs);
        return vs.vars;
    }
    
    
    /**
     * @return set of variables used in a formula
     */    
    // varSet #2: using an anonymous inner class and a final local variable
    public static Set<String> varSet2 (Formula f) {
        final Set<String> vars = new HashSet<String>();
        f.accept(new Formula.Visitor<Void>() {
            public Void on(Var v) { vars.add(v.name); return null; }
            public Void on(Not n) { n.f.accept(this); return null; }
            public Void on(And a) { a.left.accept(this); a.right.accept(this); return null; }
            public Void on(Or o)  { o.left.accept(this); o.right.accept(this); return null; }        
        });
        return vars;
    }
}
