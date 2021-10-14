package net.sakuragame.megumi.justlevel.file.sub;

import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.megumi.justlevel.JustLevel;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class MessageFile {
    private static YamlConfiguration message;

    public static List<String> help;
    public static List<String> adminHelp;
    public static List<String> playerInfo;

    public static String noPermission;
    public static String expChange;

    public static void init() {
        message = JustLevel.getInstance().getFileManager().getMessage();

        help = getStringList("help");
        adminHelp = getStringList("admin-help");
        playerInfo = getStringList("player-info");

        noPermission = getString("message.no-permission");
        expChange = getString("message.exp-change");
    }

    private static String getString(String path) {
        return MegumiUtil.onReplace(message.getString(path));
    }

    private static List<String> getStringList(String path) {
        return MegumiUtil.onReplace(message.getStringList(path));
    }

}
