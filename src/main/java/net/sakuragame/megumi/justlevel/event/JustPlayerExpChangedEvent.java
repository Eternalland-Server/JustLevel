package net.sakuragame.megumi.justlevel.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class JustPlayerExpChangedEvent extends IEvent {

    private final Player player;
    private final int level;
    private final double exp;
    private final double expChange;

    public JustPlayerExpChangedEvent(Player player, int level, double exp, double expChange) {
        this.player = player;
        this.level = level;
        this.exp = exp;
        this.expChange = expChange;
    }

    private static final HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
