package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.justwei.util.MegumiUtil;
import ink.ptms.zaphkiel.ZaphkielAPI;
import ink.ptms.zaphkiel.api.ItemStream;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTag;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTagData;
import net.sakuragame.eternal.gemseconomy.api.GemsEconomyAPI;
import net.sakuragame.eternal.gemseconomy.currency.EternalCurrency;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.PropPickupEvent;
import net.sakuragame.eternal.justlevel.core.PropGenerate;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class PropListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPickup(PlayerPickupItemEvent e) {
        if (e.isCancelled()) return;

        Player player = e.getPlayer();
        Item item = e.getItem();

        ItemStack itemStack = item.getItemStack();
        int amount = itemStack.getAmount();
        ItemStream itemStream = ZaphkielAPI.INSTANCE.read(itemStack);
        if (itemStream.isVanilla()) return;

        ItemTag itemTag = itemStream.getZaphkielData();
        ItemTagData tagData = itemTag.getDeep("parkour.type");
        if (tagData == null) return;

        int type = tagData.asInt();
        int value = itemTag.getDeep("parkour.value").asInt() * amount;

        item.remove();
        e.setCancelled(true);

        PropGenerate.ItemEnum propItemEnum = PropGenerate.ItemEnum.match(type);
        if (propItemEnum == null) return;

        PropPickupEvent event = new PropPickupEvent(player, type, value);
        event.call();

        switch (propItemEnum) {
            case EXP:
                if (event.isCancelled()) break;
                value = (int) (value * (1 + (JustLevelAPI.getTotalStage(player) / 10d)));
                JustLevelAPI.addExp(player, value);
                break;
            case LEVEL:
                if (!event.isCancelled()) JustLevelAPI.addLevel(player, value);
                MessageAPI.sendInformMessage(player, "§8[§e+§8] " + propItemEnum.getColor() + value + "级");
                break;
            case MONEY:
                if (!event.isCancelled()) GemsEconomyAPI.deposit(player.getUniqueId(), value, EternalCurrency.Money);
                MessageAPI.sendInformMessage(player, "§8[§e+§8] " + propItemEnum.getColor() + value + "金币");
                break;
            case COINS:
                if (!event.isCancelled()) GemsEconomyAPI.deposit(player.getUniqueId(), value, EternalCurrency.Coins);
                MessageAPI.sendInformMessage(player, "§8[§e+§8] " + propItemEnum.getColor() + value + "点劵");
                break;
        }

        player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_FRAME_FILL, 1f, 1f);
    }

    @EventHandler
    public void onEdit(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!e.getAction().name().startsWith("LEFT")) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (MegumiUtil.isEmpty(item)) return;
        if (item.getType() != Material.BLAZE_ROD) return;

        Block block = e.getClickedBlock();
        if (block == null) return;

        e.setCancelled(true);

        List<String> lore = item.getItemMeta().getLore();
        int type = Integer.parseInt(lore.get(0));
        int value = Integer.parseInt(lore.get(1));
        int amount = Integer.parseInt(lore.get(2));

        Location location = new Location(block.getWorld(), block.getX() + 0.5, block.getY() + 1, block.getZ() + 0.5);

        JustLevel.getPropGenerate().spawn(type, location, value, amount);
    }
}
