package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerCardUsedEvent extends JustEvent {

    private final String card;

    public PlayerCardUsedEvent(Player who, String card) {
        super(who);
        this.card = card;
    }
}
