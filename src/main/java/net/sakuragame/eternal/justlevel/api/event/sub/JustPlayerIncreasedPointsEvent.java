package net.sakuragame.eternal.justlevel.api.event.sub;

import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import net.sakuragame.eternal.justlevel.level.LevelDefine;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class JustPlayerIncreasedPointsEvent extends JustLevelEvent {

    private final LevelDefine define;
    private final int points;

    public JustPlayerIncreasedPointsEvent(Player player, LevelDefine define, int points) {
        super(player);
        this.define = define;
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
