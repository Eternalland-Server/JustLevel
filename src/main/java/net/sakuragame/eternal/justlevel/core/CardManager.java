package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.dragoncore.util.Pair;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.PlayerCardUsedEvent;
import net.sakuragame.eternal.justlevel.core.level.Card;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import net.sakuragame.eternal.justmessage.icon.IconProperty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

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

        int duration = ConfigFile.additionCard.get(card).getDuration();
        JustLevel.getStorageManager().setUseCard(uuid, card, duration);
        add(uuid, card, duration);

        PlayerCardUsedEvent event = new PlayerCardUsedEvent(Bukkit.getPlayer(uuid), card);
        event.call();
    }

    public static void add(Player player, String cID, long expire) {
        add(player.getUniqueId(), cID, expire);
    }

    public static void add(UUID uuid, String cID, long expire) {
        Player player = Bukkit.getPlayer(uuid);
        Card card = ConfigFile.additionCard.get(cID);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                using.remove(uuid);
                MessageAPI.delIcon(player, cID);
            }
        }.runTaskLaterAsynchronously(plugin, expire * 20);

        using.put(uuid, new Pair<>(cID, task.getTaskId()));
        MessageAPI.addIcon(player, cID, new IconProperty(card.getTexture(), card.getName()));
    }

    public static String getCurrentUse(Player player) {
        return getCurrentUse(player.getUniqueId());
    }

    public static String getCurrentUse(UUID uuid) {
        Pair<String, Integer> current = using.get(uuid);
        if (current == null) return null;

        return current.getKey();
    }

    public static void clear(UUID uuid) {
        Pair<String, Integer> data = using.remove(uuid);
        if (data == null) return;

        Bukkit.getScheduler().cancelTask(data.getValue());
    }

}
