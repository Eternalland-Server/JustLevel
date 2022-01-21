package net.sakuragame.eternal.justlevel.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class PlayerDataLoadEvent extends JustEvent {

    private final static HandlerList handlerList = new HandlerList();

    public PlayerDataLoadEvent(Player who) {
        super(who);
    }

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
