package net.sakuragame.eternal.justlevel.listener;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.sub.JLPlayerInitFinishedEvent;
import net.sakuragame.eternal.justlevel.level.PlayerLevelData;
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

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> initPlayerData(player), 10);
    }

    private void initPlayerData(Player player) {
        if (player == null) return;
        PlayerLevelData data = plugin.getStorageManager().getPlayerData(player);
        plugin.getPlayerData().put(player.getUniqueId(), data);

        JLPlayerInitFinishedEvent event = new JLPlayerInitFinishedEvent(player);
        event.call();

        Bukkit.getScheduler().runTaskLater(plugin, data::syncPlaceHolder, 40);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            PlayerLevelData data = plugin.getPlayerData().remove(uuid);
            if (data == null) return;
            data.save();
        });
    }

    @EventHandler
    public void onVanillaExpChange(PlayerExpChangeEvent e) {
        e.setAmount(0);
    }
}
