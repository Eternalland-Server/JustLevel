package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.dragoncore.util.Pair;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.PlayerCardUsedEvent;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CardManager {

    private final static JustLevel plugin = JustLevel.getInstance();
    private final static Map<UUID, Pair<String, Integer>> using = new HashMap<>();

    public static void use(Player player, String card) {
        use(player.getUniqueId(), card);
    }

    public static void use(UUID uuid, String card) {
        Pair<String, Integer> current = using.get(uuid);
        if (current != null) {
            int id = current.getValue();
            Bukkit.getScheduler().cancelTask(id);
        }

        int duration = ConfigFile.additionCard.get(card).getKey();
        add(uuid, card, duration);

        PlayerCardUsedEvent event = new PlayerCardUsedEvent(Bukkit.getPlayer(uuid), card);
        event.call();
    }

    public static void add(Player player, String card, long expire) {
        add(player.getUniqueId(), card, expire);
    }

    public static void add(UUID uuid, String card, long expire) {
        int id = new BukkitRunnable() {
            @Override
            public void run() {
                using.remove(uuid);
            }
        }.runTaskLaterAsynchronously(plugin, expire * 20).getTaskId();

        using.put(uuid, new Pair<>(card, id));
    }

    public static String getCurrentUse(Player player) {
        return getCurrentUse(player.getUniqueId());
    }

    public static String getCurrentUse(UUID uuid) {
        Pair<String, Integer> current = using.get(uuid);
        if (current == null) return null;

        return current.getKey();
    }

}
