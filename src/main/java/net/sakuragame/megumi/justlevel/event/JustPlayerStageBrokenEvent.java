package net.sakuragame.megumi.justlevel.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class JustPlayerStageBrokenEvent extends IEvent {

    private final int stage;
    private final int points;
    private final double price;

    private final static HandlerList handlerList = new HandlerList();

    public JustPlayerStageBrokenEvent(Player who, int stage, int points, double price) {
        super(who);
        this.stage = stage;
        this.points = points;
        this.price = price;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
