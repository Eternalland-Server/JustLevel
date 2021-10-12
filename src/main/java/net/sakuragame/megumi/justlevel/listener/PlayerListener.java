package net.sakuragame.megumi.justlevel.listener;

import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.event.JustPlayerExpChangedEvent;
import net.sakuragame.megumi.justlevel.event.JustPlayerUpgradedEvent;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private JustLevel plugin = JustLevel.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        plugin.getPlayerData().put(uuid, new PlayerLevelData(player));

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            PlayerLevelData data = plugin.getStorageManager().getPlayerData(player);
            plugin.getPlayerData().put(uuid, data);
        }, 10);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        plugin.getPlayerData().remove(uuid);

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
           plugin.getPlayerData().get(uuid).save();
           plugin.getPlayerData().remove(uuid);
        });
    }
}
