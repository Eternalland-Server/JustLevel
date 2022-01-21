package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerRealmChangeEvent extends JustEvent {

    private final int realm;

    public PlayerRealmChangeEvent(Player who, int realm) {
        super(who);
        this.realm = realm;
    }
}
