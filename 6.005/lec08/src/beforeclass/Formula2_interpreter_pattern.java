package beforeclass;

public class Formula2_interpreter_pattern {
    // datatype definition:
    //    Formula = Var(name:String) 
    //              + Not(f:Formula)
    //              + And(left:Formula, right:Formula)
    //              + Or(left:Formula, right:Formula)
        
    public static interface Formula {
        public boolean eval(Env env);
    }
    
    public static class Var implements Formula {
        public final String name;
        public Var(String name) { this.name = name; }
        public boolean eval(Env env) { return env.lookup(name); }
    }

    public static class Not implements Formula {
        public final Formula f;
        public Not(Formula f) { this.f = f; }
        public boolean eval(Env env) { return !f.eval(env); }
    }

    public static class And implements Formula {
        public final Formula left, right;
        public And(Formula left, Formula right) { this.left = left; this.right = right; }
        public boolean eval(Env env) { return left.eval(env) && right.eval(env); }
    }

    public static class Or implements Formula {
        public final Formula left, right;
        public Or(Formula left, Formula right) { this.left = left; this.right = right; }
        public boolean eval(Env env) { return left.eval(env) || right.eval(env); }
    }
    
    
    public static interface Env {
        public boolean lookup(String name);
    }
}
