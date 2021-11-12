package net.sakuragame.eternal.justlevel.api.event.sub;

import lombok.Getter;
import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class JustPlayerExpIncreasedEvent extends JustLevelEvent {

    private final int level;
    private final double exp;
    private final double increase;

    public JustPlayerExpIncreasedEvent(Player player, int level, double exp, double increase) {
        super(player);
        this.level = level;
        this.exp = exp;
        this.increase = increase;
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
