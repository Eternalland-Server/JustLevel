package net.sakuragame.eternal.justlevel.api.event.sub;

import lombok.Getter;
import lombok.Setter;
import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import net.sakuragame.eternal.justlevel.level.LevelDefine;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class JustPlayerIncreasePointsEvent extends JustLevelEvent implements Cancellable {

    private final LevelDefine define;
    private int points;
    private boolean cancel;

    public JustPlayerIncreasePointsEvent(Player player, LevelDefine define, int points) {
        super(player);
        this.define = define;
        this.points = points;
        this.cancel = false;
    }

    private static final HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return getHandlerList();
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return this.cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
