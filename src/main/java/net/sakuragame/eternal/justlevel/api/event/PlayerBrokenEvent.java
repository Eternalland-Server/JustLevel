package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

public class PlayerBrokenEvent {

    @Getter
    public static class Realm extends JustEvent {

        private final int level;

        public Realm(Player who, int level) {
            super(who);
            this.level = level;
        }
    }

    @Getter
    public static class Stage extends JustEvent {

        private final int level;

        public Stage(Player who, int level) {
            super(who);
            this.level = level;
        }
    }
}
