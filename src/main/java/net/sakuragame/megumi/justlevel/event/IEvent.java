package net.sakuragame.megumi.justlevel.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public abstract class IEvent extends Event {

    public void call() {
        Bukkit.getPluginManager().callEvent(this);
    }
}
