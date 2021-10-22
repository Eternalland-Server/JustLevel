package net.sakuragame.megumi.justlevel.event;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerEvent;

public abstract class IEvent extends PlayerEvent {

    public IEvent(Player who) {
        super(who);
    }

    public void call() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
