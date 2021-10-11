package com.taylorswiftcn.megumi.justlevel.listener;

import com.taylorswiftcn.megumi.justlevel.JustLevel;
import com.taylorswiftcn.megumi.justlevel.level.PlayerLevelData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private JustLevel plugin = JustLevel.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            PlayerLevelData data = plugin.getStorageManager().getPlayerData(player);
            plugin.getPlayerData().put(player.getUniqueId(), data);
        }, 10);
    }
}
