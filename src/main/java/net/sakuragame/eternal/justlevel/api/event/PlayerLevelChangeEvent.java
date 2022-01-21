package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerLevelChangeEvent extends JustEvent {

    private final int oldLevel;
    private final int newLevel;

    public PlayerLevelChangeEvent(Player who, int oldLevel, int newLevel) {
        super(who);
        this.oldLevel = oldLevel;
        this.newLevel = newLevel;
    }
}
