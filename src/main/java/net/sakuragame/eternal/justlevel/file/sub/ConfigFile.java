package net.sakuragame.eternal.justlevel.file.sub;

import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.eternal.dragoncore.util.Pair;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.core.level.Realm;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigFile {
    private static YamlConfiguration config;

    public static String Prefix;
    public static Integer realm_layer;
    public static Integer stage_layer;
    public static Integer stage_level;
    public static String level_formula;
    public static Map<String, Double> expAddition;
    public static Map<Integer, Realm> realmSetting;
    public static Map<String, Pair<Integer, Double>> additionCard;

    public static void init() {
        config = JustLevel.getFileManager().getConfig();

        Prefix = getString("Prefix");
        realm_layer = 21;
        stage_layer = 10;
        stage_level = 200;
        level_formula = config.getString("level-formula");
        loadExpAddition();
        loadRealmSetting();
        loadAdditionCard();
    }

    private static String getString(String path) {
        return MegumiUtil.onReplace(config.getString(path));
    }

    private static List<String> getStringList(String path) {
        return MegumiUtil.onReplace(config.getStringList(path));
    }

    private static void loadExpAddition() {
        expAddition = new LinkedHashMap<>();

        ConfigurationSection section = config.getConfigurationSection("exp-addition");
        if (section == null) return;

        for (String s : section.getKeys(false)) {
            double rate = section.getDouble(s);
            expAddition.put(s, rate);
        }
    }

    private static void loadRealmSetting() {
        realmSetting = new HashMap<>();

        ConfigurationSection section = config.getConfigurationSection("setting");
        if (section == null) return;

        for (String s : section.getKeys(false)) {
            int layer = Integer.parseInt(s);
            String name = section.getString(s + ".name");
            String prefix = section.getString(s + ".prefix");
            int stageConsume = section.getInt(s + ".stage-consume");
            int realmConsume = section.getInt(s + ".realm-consume");
            int stageBreakPrice = section.getInt(s + ".stage-break-price");
            int realmBreakPrice = section.getInt(s + ".realm-break-price");
            realmSetting.put(layer, new Realm(layer, name, prefix, stageConsume, realmConsume, stageBreakPrice, realmBreakPrice));
        }
    }

    private static void loadAdditionCard() {
        YamlConfiguration yaml = JustLevel.getFileManager().getCard();
        additionCard = new HashMap<>();

        for (String key : yaml.getKeys(false)) {
            int duration = yaml.getInt(key + ".duration");
            double addition = yaml.getDouble(key + ".addition");

            additionCard.put(key, new Pair<>(duration, addition));
        }
    }
}
