package scripting;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class Python {
    public static void main(String[] args) {
        ScriptEngine engine = makeScriptEngine();
        
        if (args.length == 0) {
            readEvalPrintLoop(engine);
        } else if (args.length == 1) {
            runScriptFile(engine, args[0]);
        } else {
            System.err.println("usage: <filename.py> to run a Python script from a file, or no arguments to get an interactive prompt");
        }
    }

    public static ScriptEngine makeScriptEngine() {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("python");
        
        // fix the engine's default error output so that it flushes after every write;
        // otherwise we don't see errors
        ScriptContext context = engine.getContext();
        context.setErrorWriter(new FlushingWriter(context.getErrorWriter()));
        return engine;
    }
    
    
    public static void readEvalPrintLoop(ScriptEngine engine) {
        try {
            engine.eval("import code");
            engine.eval("code.interact(banner='Welcome to my new Python interpreter',local=locals())");
        } catch (ScriptException e) {
            // Shouldn't happen (interactive Python errors are reported to the user through the error writer).
            // If this happens, it's a bug in our code above.
            throw new RuntimeException(e);
        }
    }
    
    public static void runScriptFile(ScriptEngine engine, String filename) {
        try {
            File f = new File(filename);
            FileReader r = new FileReader(f);
            try {
                engine.eval(r);
            } finally {
                r.close();
            }
        } catch (ScriptException e) {
            System.err.println(e);            
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
