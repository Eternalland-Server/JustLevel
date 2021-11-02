package net.sakuragame.megumi.justlevel.listener;

import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final JustLevel plugin = JustLevel.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> loadPlayerData(player), 10);
    }

    private void loadPlayerData(Player player) {
        if (player == null) return;
        PlayerLevelData data = plugin.getStorageManager().getPlayerData(player);
        plugin.getPlayerData().put(player.getUniqueId(), data);

        Bukkit.getScheduler().runTaskLater(plugin, data::syncPlaceHolder, 40);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
           plugin.getPlayerData().get(uuid).save();
           plugin.getPlayerData().remove(uuid);
        });
    }

    @EventHandler
    public void onVanillaExpChange(PlayerExpChangeEvent e) {
        e.setAmount(0);
    }
}
