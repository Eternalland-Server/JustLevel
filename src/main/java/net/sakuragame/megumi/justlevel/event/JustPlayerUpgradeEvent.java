package net.sakuragame.megumi.justlevel.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class JustPlayerUpgradeEvent extends IEvent implements Cancellable {

    private final Player player;
    private final int level;
    private int upgrade;
    private boolean cancel;

    public JustPlayerUpgradeEvent(Player player, int level, int upgrade) {
        this.player = player;
        this.level = level;
        this.upgrade = upgrade;
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
