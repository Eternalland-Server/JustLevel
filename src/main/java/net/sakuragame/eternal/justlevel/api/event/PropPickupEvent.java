package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PropPickupEvent extends JustEvent {

    private final int type;
    private final int value;

    public PropPickupEvent(Player who, int type, int value) {
        super(who);
        this.type = type;
        this.value = value;
    }
}
