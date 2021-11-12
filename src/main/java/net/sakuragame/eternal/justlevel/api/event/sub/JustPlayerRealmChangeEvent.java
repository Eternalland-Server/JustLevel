package net.sakuragame.eternal.justlevel.api.event.sub;

import lombok.Getter;
import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class JustPlayerRealmChangeEvent extends JustLevelEvent {

    private final int oldRealm;
    private final int newRealm;

    public JustPlayerRealmChangeEvent(Player player, int oldRealm, int newRealm) {
        super(player);
        this.oldRealm = oldRealm;
        this.newRealm = newRealm;
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
