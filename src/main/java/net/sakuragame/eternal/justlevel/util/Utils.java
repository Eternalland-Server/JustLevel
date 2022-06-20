package net.sakuragame.eternal.justlevel.util;

import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.entity.Player;

public class Utils {

    public static double getUpgradeRequireExp(int realm, int stage, int level) {
        int total = (realm - 1) * 2000 + (stage - 1) * 200 + level;
        double difficulty = total / (2940d / realm) + realm + stage;
        int result = (int) (total / Math.pow(difficulty / 4, -1));

        return result + 20;
    }

    public static double getExpAddition(Player player) {
        for (String s : ConfigFile.expAddition.keySet()) {
            if (!player.hasPermission(s)) continue;
            return ConfigFile.expAddition.get(s);
        }

        return 0;
    }
}
