package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerPointChangeEvent extends JustEvent {

    private final int points;

    public PlayerPointChangeEvent(Player who, int points) {
        super(who);
        this.points = points;
    }

    public static class Realm extends PlayerPointChangeEvent {

        public Realm(Player who, int realmPoints) {
            super(who, realmPoints);
        }
    }

    public static class Stage extends PlayerPointChangeEvent {

        public Stage(Player who, int stagePoints) {
            super(who, stagePoints);
        }
    }
}
