package com.taylorswiftcn.megumi.justlevel.file.sub;

import com.taylorswiftcn.justwei.util.MegumiUtil;
import com.taylorswiftcn.megumi.justlevel.JustLevel;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class ConfigFile {
    private static YamlConfiguration config;

    public static String Prefix;
    public static Integer realm_layer;
    public static Integer stage_level;
    public static String level_formula;

    public static void init() {
        config = JustLevel.getInstance().getFileManager().getConfig();

        Prefix = getString("Prefix");
        realm_layer = 21;
        stage_level = 200;
        level_formula = config.getString("level-formula");
    }

    private static String getString(String path) {
        return MegumiUtil.onReplace(config.getString(path));
    }

    private static List<String> getStringList(String path) {
        return MegumiUtil.onReplace(config.getStringList(path));
    }
}
