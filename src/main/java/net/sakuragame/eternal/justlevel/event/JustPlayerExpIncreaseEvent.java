package net.sakuragame.eternal.justlevel.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class JustPlayerExpIncreaseEvent extends JustLevelEvent implements Cancellable {

    private final int level;
    private final double exp;
    private double increase;
    private boolean cancel;

    public JustPlayerExpIncreaseEvent(Player player, int level, double exp, double increase) {
        super(player);
        this.level = level;
        this.exp = exp;
        this.increase = increase;
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
