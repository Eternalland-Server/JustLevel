package com.taylorswiftcn.megumi.justlevel.util;

import com.taylorswiftcn.megumi.justlevel.JustLevel;
import com.taylorswiftcn.megumi.justlevel.file.sub.ConfigFile;

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
        for (int i = 0; i < ConfigFile.stage_level; i++) {
            try {
                double require = (Double) script.eval(ConfigFile.level_formula.replace("level", String.valueOf(i)));
                exp.put(i, require);
            }
            catch (ScriptException e) {
                e.printStackTrace();
            }
        }
    }

    public static double getUpgradeRequireExp(int level) {
        return exp.get(level);
    }
}
