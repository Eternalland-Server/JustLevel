package net.sakuragame.megumi.justlevel.event;

import com.taylorswiftcn.justwei.event.IEvent;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import lombok.Getter;
import org.bukkit.event.HandlerList;

@Getter
public class JustPlayerUpgradedEvent extends IEvent {

    private final PlayerLevelData playerData;
    private final int upgrade;
    private final int oldLevel;
    private final int newLevel;

    public JustPlayerUpgradedEvent(PlayerLevelData playerData, int upgrade, int oldLevel, int newLevel) {
        this.playerData = playerData;
        this.upgrade = upgrade;
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
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
