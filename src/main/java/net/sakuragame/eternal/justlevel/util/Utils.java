package net.sakuragame.eternal.justlevel.util;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private final static JustLevel plugin = JustLevel.getInstance();
    private final static ScriptEngine script = new ScriptEngineManager().getEngineByName("javascript");
    private final static Map<Integer, Double> exp = new HashMap<>();

    public static void conversionExp() {
        exp.clear();
        for (int i = 0; i <= ConfigFile.stage_level; i++) {
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

    public static double getExpAddition(Player player, double exp) {
        for (String s : ConfigFile.expAddition.keySet()) {
            String permission = plugin.getName().toLowerCase() + "." + s;
            if (!player.hasPermission(permission)) continue;
            double rate = ConfigFile.expAddition.get(s);
            exp = exp * (rate - 1);
            return exp;
        }

        return exp;
    }

    public static double getLevelUpExp(int level) {
        return exp.get(level);
    }
}
