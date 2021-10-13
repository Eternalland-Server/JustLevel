package net.sakuragame.megumi.justlevel.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class JustPlayerExpChangeEvent extends IEvent implements Cancellable {

    private final Player player;
    private final int level;
    private final double exp;
    private double expChange;
    private boolean cancel;

    public JustPlayerExpChangeEvent(Player player, int level, double exp, double expChange) {
        this.player = player;
        this.level = level;
        this.exp = exp;
        this.expChange = expChange;
        this.cancel = false;
    }

    private static final HandlerList handlerList = new HandlerList();

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
