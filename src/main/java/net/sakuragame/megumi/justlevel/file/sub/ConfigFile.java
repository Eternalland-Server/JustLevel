package net.sakuragame.megumi.justlevel.file.sub;

import com.taylorswiftcn.justwei.util.ItemBuilder;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.megumi.justlevel.JustLevel;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigFile {
    private static YamlConfiguration config;

    public static final String ID_TAG = "JUST_ID";

    public static String Prefix;
    public static Integer realm_layer;
    public static Integer stage_level;
    public static String level_formula;
    public static Map<Integer, Integer> stageUpgrade;
    public static Map<Integer, Integer> realmUpgrade;
    public static Map<String, ItemStack> stone;

    public static void init() {
        config = JustLevel.getInstance().getFileManager().getConfig();

        Prefix = getString("Prefix");
        realm_layer = 21;
        stage_level = 200;
        level_formula = config.getString("level-formula");
        loadStageUpgrade();
        loadRealmUpgrade();
        stone.put("stage", new ItemBuilder(config.getConfigurationSection("stone.stage")).setNBT(ID_TAG, "stage").build());
        stone.put("realm", new ItemBuilder(config.getConfigurationSection("stone.realm")).setNBT(ID_TAG, "realm").build());
    }

    private static String getString(String path) {
        return MegumiUtil.onReplace(config.getString(path));
    }

    private static List<String> getStringList(String path) {
        return MegumiUtil.onReplace(config.getStringList(path));
    }

    private static void loadStageUpgrade() {
        stageUpgrade = new HashMap<>();

        ConfigurationSection section = config.getConfigurationSection("stage-upgrade");
        if (section == null) return;

        for (String s : section.getKeys(false)) {
            int count = section.getInt(s);
            stageUpgrade.put(Integer.parseInt(s), count);
        }
    }

    private static void loadRealmUpgrade() {
        realmUpgrade = new HashMap<>();

        ConfigurationSection section = config.getConfigurationSection("real-upgrade");
        if (section == null) return;

        for (String s : section.getKeys(false)) {
            int count = section.getInt(s);
            realmUpgrade.put(Integer.parseInt(s), count);
        }
    }
}
