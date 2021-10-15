package net.sakuragame.megumi.justlevel.event;

import lombok.Getter;
import lombok.Setter;
import net.sakuragame.megumi.justlevel.level.TierType;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class JustPlayerIncreasePointsEvent extends IEvent implements Cancellable {

    private final Player player;
    private final TierType type;
    private int points;
    private boolean cancel;

    public JustPlayerIncreasePointsEvent(Player player, TierType type, int points) {
        this.player = player;
        this.type = type;
        this.points = points;
        this.cancel = false;
    }

    private static final HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}