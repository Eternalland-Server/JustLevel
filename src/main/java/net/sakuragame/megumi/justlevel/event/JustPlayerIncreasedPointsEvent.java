package net.sakuragame.megumi.justlevel.event;

import net.sakuragame.megumi.justlevel.level.LevelType;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class JustPlayerIncreasedPointsEvent extends IEvent {

    private final LevelType type;
    private final int points;

    public JustPlayerIncreasedPointsEvent(Player player, LevelType type, int points) {
        super(player);
        this.type = type;
        this.points = points;
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
