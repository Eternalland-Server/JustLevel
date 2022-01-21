package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerStageChangeEvent extends JustEvent {

    private final int stage;

    public PlayerStageChangeEvent(Player who, int stage) {
        super(who);
        this.stage = stage;
    }
}
