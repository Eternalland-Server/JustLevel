package net.sakuragame.megumi.justlevel.event;

import com.taylorswiftcn.justwei.event.IEvent;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import lombok.Getter;
import org.bukkit.event.HandlerList;

@Getter
public class JustPlayerExpChangedEvent extends IEvent {

    private final PlayerLevelData playerData;
    private final double expChange;

    public JustPlayerExpChangedEvent(PlayerLevelData playerData, double expChange) {
        this.playerData = playerData;
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
