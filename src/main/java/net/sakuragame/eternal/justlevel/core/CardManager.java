package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.dragoncore.network.PacketSender;
import net.sakuragame.eternal.dragoncore.util.Pair;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.PlayerCardUsedEvent;
import net.sakuragame.eternal.justlevel.core.level.Card;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import net.sakuragame.eternal.justmessage.gains.GainsProperty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CardManager {

    private final static JustLevel plugin = JustLevel.getInstance();
    private final static Map<UUID, Pair<String, Integer>> using = new HashMap<>();

    public final static DecimalFormat FORMAT = new DecimalFormat("0");

    public static void load(Player player) {
        UUID uuid = player.getUniqueId();
        Pair<String, Long> result = JustLevel.getStorageManager().getUseCard(uuid);
        if (result == null) return;
        CardManager.add(uuid, result.getKey(), Math.toIntExact(result.getValue()));
    }

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

    public static void add(Player player, String cardID, int duration) {
        add(player.getUniqueId(), cardID, duration);
    }

    public static void add(UUID uuid, String cardID, int duration) {
        Player player = Bukkit.getPlayer(uuid);
        Card card = ConfigFile.additionCard.get(cardID);

        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                using.remove(uuid);
            }
        }.runTaskLaterAsynchronously(plugin, duration * 20L);

        using.put(uuid, new Pair<>(cardID, task.getTaskId()));
        sendCardPlaceholder(player, card.getAddition());
        MessageAPI.applyGains(player, "exp_card", new GainsProperty(card.getDisplay(), System.currentTimeMillis() + duration * 1000L));
    }

    public static void sendCardPlaceholder(Player player, double value) {
        String s = "+" + FORMAT.format(value * 100) + "%";
        PacketSender.sendSyncPlaceholder(player, new HashMap<String, String>() {{ put("exp_addition_card", s); }});
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
