package net.sakuragame.eternal.justlevel.file.sub;

import com.taylorswiftcn.justwei.util.ItemBuilder;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.level.LevelType;
import net.sakuragame.eternal.justlevel.level.RealmSetting;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigFile {
    private static YamlConfiguration config;

    public static final String ID_TAG = "JUST_LEVEL_ID";

    public static String Prefix;
    public static Integer realm_layer;
    public static Integer stage_layer;
    public static Integer stage_level;
    public static String level_formula;
    public static Map<String, Double> expAddition;
    public static Map<Integer, RealmSetting> realmSetting;
    public static Map<String, ItemStack> stone;

    public static void init() {
        config = JustLevel.getInstance().getFileManager().getConfig();

        Prefix = getString("Prefix");
        realm_layer = 21;
        stage_layer = 10;
        stage_level = 200;
        level_formula = config.getString("level-formula");
        loadExpAddition();
        loadRealmSetting();
        stone = new HashMap<>();
        stone.put(LevelType.Stage.getId(), new ItemBuilder(config.getConfigurationSection("stone.stage")).setNBT(ID_TAG, LevelType.Stage.getId()).build());
        stone.put(LevelType.Realm.getId(), new ItemBuilder(config.getConfigurationSection("stone.realm")).setNBT(ID_TAG, LevelType.Realm.getId()).build());
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
            int stageConsume = section.getInt(s + ".stage-consume");
            int realmConsume = section.getInt(s + ".realm-consume");
            int stageBreakPrice = section.getInt(s + ".stage-break-price");
            int realmBreakPrice = section.getInt(s + ".realm-break-price");
            realmSetting.put(layer, new RealmSetting(layer, name, stageConsume, realmConsume, stageBreakPrice, realmBreakPrice));
        }
    }
}