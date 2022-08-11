package net.sakuragame.eternal.justlevel.listener;

import ink.ptms.zaphkiel.ZaphkielAPI;
import ink.ptms.zaphkiel.api.ItemStream;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTag;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTagData;
import net.sakuragame.eternal.gemseconomy.api.GemsEconomyAPI;
import net.sakuragame.eternal.gemseconomy.currency.EternalCurrency;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.core.PropGenerate;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class PropListener implements Listener {

    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
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

        PropGenerate.Item propItem = PropGenerate.Item.match(type);
        if (propItem == null) return;

        switch (propItem) {
            case EXP:
                JustLevelAPI.addExp(player, value);
                return;
            case LEVEL:
                JustLevelAPI.addLevel(player, value);
                MessageAPI.sendInformMessage(player, "§8[§e+§8] " + propItem.getColor() + value + "级");
                return;
            case MONEY:
                GemsEconomyAPI.deposit(player.getUniqueId(), value, EternalCurrency.Money);
                MessageAPI.sendInformMessage(player, "§8[§e+§8] " + propItem.getColor() + value + "金币");
                return;
            case COINS:
                GemsEconomyAPI.deposit(player.getUniqueId(), value, EternalCurrency.Coins);
                MessageAPI.sendInformMessage(player, "§8[§e+§8] " + propItem.getColor() + value + "点劵");
        }
    }
}
