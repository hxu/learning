import javax.script.ScriptEngine;

import scripting.Python;

public class Main {    
    public static void main(String[] args) {
        ScriptEngine engine = Python.makeScriptEngine();
        Python.runScriptFile(engine, "src/music/MusicLanguage.py");
        Python.readEvalPrintLoop(engine, "Music Language interpreter");
    }
}