package net.sakuragame.eternal.justlevel.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;

public class PlayerExpIncreaseEvent {

    @Getter
    public static class Pre extends JustEvent {

        private final double amount;
        private double addition;

        public Pre(Player player, double amount) {
            super(player);
            this.amount = amount;
            this.addition = 0;
        }

        public void addAddition(double value) {
            this.addition += value;
        }
    }

    @Getter
    public static class Post extends JustEvent {

        private final double amount;
        private final double addition;

        public Post(Player player, double amount, double addition) {
            super(player);
            this.amount = amount;
            this.addition = addition;
        }
    }
}
