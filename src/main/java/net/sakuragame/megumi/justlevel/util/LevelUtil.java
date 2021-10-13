package net.sakuragame.megumi.justlevel.util;

import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class LevelUtil {

    private static JustLevel plugin = JustLevel.getInstance();
    private static ScriptEngine script = new ScriptEngineManager().getEngineByName("javascript");
    private static Map<Integer, Double> exp = new HashMap<>();

    public static void conversionExp() {
        exp.clear();
        for (int i = 1; i <= ConfigFile.stage_level; i++) {
            try {
                double require = Double.parseDouble(script.eval(ConfigFile.level_formula.replace("level", String.valueOf(i))).toString());
                exp.put(i, require);
            }
            catch (ScriptException e) {
                e.printStackTrace();
            }
        }

        double last = exp.get(ConfigFile.stage_level);
        last = Math.ceil(last / 1000) * 1000;
        exp.put(ConfigFile.stage_level, last);
    }

    public static double getUpgradeRequireExp(int level) {
        return exp.get(level);
    }
}
