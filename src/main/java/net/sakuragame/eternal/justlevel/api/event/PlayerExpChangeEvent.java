package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerExpChangeEvent extends JustEvent {

    private final double exp;

    public PlayerExpChangeEvent(Player who, double exp) {
        super(who);
        this.exp = exp;
    }
}
