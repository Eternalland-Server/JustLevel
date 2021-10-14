package net.sakuragame.megumi.justlevel.level;

import net.sakuragame.megumi.justlevel.JustLevel;
import org.bukkit.entity.Player;

public class LevelManager {

    private static JustLevel plugin = JustLevel.getInstance();

    public static void addExp(Player player, double exp) {
        plugin.getPlayerData().get(player.getUniqueId()).addExp(exp);
    }
}
