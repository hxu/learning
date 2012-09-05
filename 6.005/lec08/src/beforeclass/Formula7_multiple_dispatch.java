package beforeclass;

public class Formula7_multiple_dispatch {
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
     * @return true if f1 and f2 are syntactically identical
     */
    // Demonstrates multiple dispatch using visitors.  Rarely used!  Not often
    // worth the complexity in practice, but worth understanding how it works.    
    public static boolean same(final Formula f1, final Formula f2) {
        return f1.accept(new Formula.Visitor<Boolean>() {
            public Boolean on(final Var v1) { 
                return f2.accept(new Formula.Visitor<Boolean>() {
                    public Boolean on(Var v2) { return v1.name.equals(v2.name); }
                    public Boolean on(Not n2) { return false; }
                    public Boolean on(And a2) { return false; }
                    public Boolean on(Or o2)  { return false; }                
                });
            }
            public Boolean on(final Not n1) { 
                return f2.accept(new Formula.Visitor<Boolean>() {
                    public Boolean on(Var v2) { return false; }
                    public Boolean on(Not n2) { return same(n1.f, n2.f); }
                    public Boolean on(And a2) { return false; }
                    public Boolean on(Or o2)  { return false; }                
                });
            }
            public Boolean on(final And a1) { 
                return f2.accept(new Formula.Visitor<Boolean>() {
                    public Boolean on(Var v2) { return false; }
                    public Boolean on(Not n2) { return false; }
                    public Boolean on(And a2) { return same(a1.left, a2.left) && same(a1.right, a2.right); }
                    public Boolean on(Or o2)  { return false; }                
                });
            }
            public Boolean on(final Or o1) { 
                return f2.accept(new Formula.Visitor<Boolean>() {
                    public Boolean on(Var v2) { return false; }
                    public Boolean on(Not n2) { return false; }
                    public Boolean on(And a2) { return false; }
                    public Boolean on(Or o2)  { return same(o1.left, o2.left) && same(o1.right, o2.right); }                
                });
            }
        });
    }    
}
