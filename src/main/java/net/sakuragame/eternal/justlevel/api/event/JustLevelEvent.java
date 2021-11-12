package net.sakuragame.eternal.justlevel.api.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class JustLevelEvent extends PlayerEvent {

    public JustLevelEvent(Player who) {
        super(who);
    }

    public void call() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
