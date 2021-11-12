package net.sakuragame.eternal.justlevel.api.event.sub;

import lombok.Getter;
import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class JustPlayerStageChangeEvent extends JustLevelEvent {

    private final int oldStage;
    private final int newStage;
    private final int realm;

    public JustPlayerStageChangeEvent(Player player, int oldStage, int newStage, int realm) {
        super(player);
        this.oldStage = oldStage;
        this.newStage = newStage;
        this.realm = realm;
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
