package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.justwei.util.MegumiUtil;
import ink.ptms.zaphkiel.ZaphkielAPI;
import ink.ptms.zaphkiel.api.Item;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.PlayerStoneUseEvent;
import net.sakuragame.eternal.justlevel.core.level.Define;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class StoneListener implements Listener {

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        PlayerLevelData account = JustLevelAPI.getUserData(player);
        if (account == null) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (MegumiUtil.isEmpty(item)) return;

        Item zapItem = ZaphkielAPI.INSTANCE.getItem(item);
        if (zapItem == null) return;

        String id = zapItem.getId();
        if (!(id.equals("stone_stage") || id.equals("stone_realm"))) return;
        e.setCancelled(true);

        switch (id) {
            case "stone_stage":
                if (player.isSneaking()) {
                    int i = item.getAmount();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    account.addStagePoints(i);
                    MessageAPI.sendInformMessage(player, "&8[&6+&8] &f" + i + " &7阶段突破点");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 0.6f, 1);

                    PlayerStoneUseEvent event = new PlayerStoneUseEvent(player, Define.Stage, i);
                    event.call();
                }
                else {
                    item.setAmount(item.getAmount() - 1);
                    account.addStagePoints(1);
                    MessageAPI.sendInformMessage(player, "&8[&6+&8] &f1 &7阶段突破点");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 0.4f, 1);

                    PlayerStoneUseEvent event = new PlayerStoneUseEvent(player, Define.Stage);
                    event.call();
                }
                break;
            case "stone_realm":
                if (player.isSneaking()) {
                    int i = item.getAmount();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    account.addRealmPoints(i);
                    MessageAPI.sendInformMessage(player, "&8[&6+&8] &f" + i + " &7境界突破点");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 0.6f, 1);

                    PlayerStoneUseEvent event = new PlayerStoneUseEvent(player, Define.Realm, i);
                    event.call();
                }
                else {
                    item.setAmount(item.getAmount() - 1);
                    account.addRealmPoints(1);
                    MessageAPI.sendInformMessage(player, "&8[&6+&8] &f1 &7境界突破点");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_XYLOPHONE, 0.4f, 1);

                    PlayerStoneUseEvent event = new PlayerStoneUseEvent(player, Define.Realm);
                    event.call();
                }
                break;
        }
    }
}
