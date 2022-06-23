package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Queue;

public class NotifyQueue extends BukkitRunnable {

    private final Player player;
    private final Queue<String> messages;

    public NotifyQueue(Player player, Queue<String> messages) {
        this.player = player;
        this.messages = messages;
    }

    @Override
    public void run() {
        if (!this.player.isOnline() || this.messages.size() == 0) {
            cancel();
            return;
        }

        MessageAPI.sendActionTip(player, this.messages.poll());
    }
}
