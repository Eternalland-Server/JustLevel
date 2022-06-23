package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.PriorityQueue;
import java.util.Queue;

public class PlayerBrokenEvent {

    @Getter
    public static class Realm extends JustEvent {

        private final int level;
        private final Queue<String> messages;

        public Realm(Player who, int level) {
            super(who);
            this.level = level;
            this.messages = new PriorityQueue<>();
        }

        public void addMessage(String msg) {
            this.messages.add(msg);
        }
    }

    @Getter
    public static class Stage extends JustEvent {

        private final int level;
        private final Queue<String> messages;

        public Stage(Player who, int level) {
            super(who);
            this.level = level;
            this.messages = new PriorityQueue<>();
        }

        public void addMessage(String msg) {
            this.messages.add(msg);
        }
    }
}
