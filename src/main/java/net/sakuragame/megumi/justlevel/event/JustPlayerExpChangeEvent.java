package net.sakuragame.megumi.justlevel.event;

import com.taylorswiftcn.justwei.event.IEvent;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class JustPlayerExpChangeEvent extends IEvent implements Cancellable {

    private final PlayerLevelData playerData;
    private double expChange;
    private boolean cancel;

    public JustPlayerExpChangeEvent(PlayerLevelData playerData, double expChange) {
        this.playerData = playerData;
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
