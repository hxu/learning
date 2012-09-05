package beforeclass;


public class Formula6_tweaks_on_visitor {
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
    
    // using an anonymous class for a visitor
    public static boolean hasNot(Formula f) {
        return f.accept(new Formula.Visitor<Boolean>() {
            public Boolean on(Var v) { return false; }
            public Boolean on(Not n) { return true; }
            public Boolean on(And a) { return hasNot(a.left) || hasNot(a.right); }
            public Boolean on(Or o) { return hasNot(o.left) || hasNot(o.right); }
        });
    }
    
        
    // passing additional parameters to a visitor
    public static boolean eval(Formula f, Env env) {
        return f.accept(new Eval(env));
    }

    static class Eval implements Formula.Visitor<Boolean> {
        private Env env;
        public Eval(Env env) { this.env = env; }
        public Boolean on(Var v) { return env.lookup(v.name); }
        public Boolean on(Not n) { return !eval(n.f, env); }
        public Boolean on(And a) { return eval(a.left, env) && eval(a.right, env); }
        public Boolean on(Or o)  { return eval(o.left, env) || eval(o.right, env); }        
    }

    // eval #2: putting the class inside the method
    public static boolean eval2(Formula f, Env env) {
        class Eval2 implements Formula.Visitor<Boolean> {
            private Env env;
            public Eval2(Env env) { this.env = env; }
            public Boolean on(Var v) { return env.lookup(v.name); }
            public Boolean on(Not n) { return !eval(n.f, env); }
            public Boolean on(And a) { return eval(a.left, env) && eval(a.right, env); }
            public Boolean on(Or o)  { return eval(o.left, env) || eval(o.right, env); }        
        }
        return f.accept(new Eval2(env));
    }

    // eval #3: using an anonymous class and final variables
    public static boolean eval3(Formula f, final Env env) {
        return f.accept(new Formula.Visitor<Boolean>() {
            public Boolean on(Var v) { return env.lookup(v.name); }
            public Boolean on(Not n) { return !eval(n.f, env); }
            public Boolean on(And a) { return eval(a.left, env) && eval(a.right, env); }
            public Boolean on(Or o)  { return eval(o.left, env) || eval(o.right, env); }        
        });
    }


    
    public static interface Env {
        public boolean lookup(String name);
    }
}
