package net.sakuragame.eternal.justlevel.api.event.sub;

import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class JLPlayerInitFinishedEvent extends JustLevelEvent {

    private final static HandlerList handlerList = new HandlerList();

    public JLPlayerInitFinishedEvent(Player who) {
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
