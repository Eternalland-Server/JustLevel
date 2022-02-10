package net.sakuragame.eternal.justlevel.util;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    private final static ScriptEngine script = new ScriptEngineManager().getEngineByName("javascript");
    private final static Map<Integer, Double> expCache = new HashMap<>();

    public static void initExp() {
        File file = new File(JustLevel.getInstance().getDataFolder(), "exp-cache.yml");
        if (file.exists()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
            String formula = yaml.getString("__formula__");
            if (formula.equals(ConfigFile.level_formula)) {
                for (String key : yaml.getKeys(false)) {
                    if (key.equals("__formula__")) continue;

                    int level = Integer.parseInt(key);
                    double exp = yaml.getDouble(key);

                    expCache.put(level, exp);
                }
            }
        }

        conversionExp();
    }

    private static void conversionExp() {
        expCache.clear();
        for (int i = 0; i <= ConfigFile.stage_level; i++) {
            try {
                double require = Double.parseDouble(script.eval(ConfigFile.level_formula.replace("level", String.valueOf(i))).toString());
                expCache.put(i, require);
            }
            catch (ScriptException e) {
                e.printStackTrace();
            }
        }

        double last = expCache.get(ConfigFile.stage_level);
        last = Math.ceil(last / 1000) * 1000;
        expCache.put(ConfigFile.stage_level, last);

        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("__formula__", ConfigFile.level_formula);
        expCache.forEach((k,v) -> yaml.set(String.valueOf(k), v));

        try {
            File file = new File(JustLevel.getInstance().getDataFolder(), "exp-cache.yml");
            if (!file.exists()) {
                file.createNewFile();
            }
            yaml.save(file);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double getExpAddition(Player player) {
        for (String s : ConfigFile.expAddition.keySet()) {
            String permission = "eternal." + s;
            if (!player.hasPermission(permission)) continue;
            return ConfigFile.expAddition.get(s);
        }

        return 0;
    }

    public static double getLevelUpExp(int level) {
        return expCache.get(level);
    }
}
