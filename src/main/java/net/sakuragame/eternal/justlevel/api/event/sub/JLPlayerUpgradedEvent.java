package net.sakuragame.eternal.justlevel.api.event.sub;

import lombok.Getter;
import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class JLPlayerUpgradedEvent extends JustLevelEvent {

    private final int oldLevel;
    private final int newLevel;

    public JLPlayerUpgradedEvent(Player player, int oldLevel, int newLevel) {
        super(player);
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
