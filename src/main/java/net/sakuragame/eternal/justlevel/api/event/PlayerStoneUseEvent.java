package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import net.sakuragame.eternal.justlevel.core.level.Define;
import org.bukkit.entity.Player;

@Getter
public class PlayerStoneUseEvent extends JustEvent {

    private final Define type;
    private final int amount;

    public PlayerStoneUseEvent(Player who, Define type) {
        super(who);
        this.type = type;
        this.amount = 1;
    }

    public PlayerStoneUseEvent(Player who, Define type, int amount) {
        super(who);
        this.type = type;
        this.amount = amount;
    }
}
