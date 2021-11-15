package net.sakuragame.eternal.justlevel.api.event.sub;

import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import net.sakuragame.eternal.justlevel.level.LevelDefine;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class JustPlayerIncreasedPointsEvent extends JustLevelEvent {

    private final LevelDefine type;
    private final int points;

    public JustPlayerIncreasedPointsEvent(Player player, LevelDefine type, int points) {
        super(player);
        this.type = type;
        this.points = points;
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
