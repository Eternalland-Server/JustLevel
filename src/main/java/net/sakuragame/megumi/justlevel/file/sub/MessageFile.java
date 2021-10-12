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

    public static void init() {
        message = JustLevel.getInstance().getFileManager().getMessage();

        help = getStringList("Help");
        adminHelp = getStringList("AdminHelp");
        playerInfo = getStringList("PlayerInfo");

        noPermission = getString("Message.NoPermission");
    }

    private static String getString(String path) {
        return MegumiUtil.onReplace(message.getString(path));
    }

    private static List<String> getStringList(String path) {
        return MegumiUtil.onReplace(message.getStringList(path));
    }


}
