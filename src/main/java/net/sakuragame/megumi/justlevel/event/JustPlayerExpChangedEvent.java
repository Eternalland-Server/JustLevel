package net.sakuragame.megumi.justlevel.event;

import com.taylorswiftcn.justwei.event.IEvent;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import lombok.Getter;
import org.bukkit.event.HandlerList;

@Getter
public class JustPlayerExpChangedEvent extends IEvent {

    private final PlayerLevelData playerData;
    private final double changeExp;

    public JustPlayerExpChangedEvent(PlayerLevelData playerData, double changeExp) {
        this.playerData = playerData;
        this.changeExp = changeExp;
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
