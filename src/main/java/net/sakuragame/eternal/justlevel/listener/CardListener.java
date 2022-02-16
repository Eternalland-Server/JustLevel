package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.justwei.util.MegumiUtil;
import ink.ptms.zaphkiel.ZaphkielAPI;
import ink.ptms.zaphkiel.api.Item;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.core.CardManager;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CardListener implements Listener {

    private List<UUID> cooldown = new ArrayList<>();

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (MegumiUtil.isEmpty(item)) return;

        Item zapItem = ZaphkielAPI.INSTANCE.getItem(item);
        if (zapItem == null) return;

        String id = zapItem.getId();
        if (!ConfigFile.additionCard.containsKey(id)) return;
        e.setCancelled(true);

        if (cooldown.contains(uuid)) {
            player.sendMessage(ConfigFile.Prefix + "经验卡冷却中，请稍后再使用!");
            return;
        }

        CardManager.use(player, id);
        item.setAmount(item.getAmount() - 1);
        cooldown.add(uuid);
        Bukkit.getScheduler().runTaskLaterAsynchronously(JustLevel.getInstance(), () -> cooldown.remove(uuid), 100);

        player.sendMessage(ConfigFile.Prefix + "你使用了: " + MegumiUtil.onReplace(zapItem.getName().get("NAME")));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        UUID uuid = e.getPlayer().getUniqueId();
        cooldown.remove(uuid);
        CardManager.clear(uuid);
    }
}
