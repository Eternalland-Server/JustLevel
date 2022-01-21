package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

public class PlayerBrokenEvent {

    @Getter
    public static class Realm extends JustEvent {

        private final int realm;

        public Realm(Player who, int realm) {
            super(who);
            this.realm = realm;
        }
    }

    @Getter
    public static class Stage extends JustEvent {

        private final int stage;

        public Stage(Player who, int stage) {
            super(who);
            this.stage = stage;
        }
    }
}
