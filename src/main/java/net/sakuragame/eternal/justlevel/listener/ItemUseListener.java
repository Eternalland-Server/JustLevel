package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.justwei.util.MegumiUtil;
import ink.ptms.zaphkiel.ZaphkielAPI;
import ink.ptms.zaphkiel.api.ItemStream;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.PlayerStoneUseEvent;
import net.sakuragame.eternal.justlevel.core.level.Define;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ItemUseListener implements Listener {

    private final Map<String, Integer> tonicProfile;

    public ItemUseListener() {
        this.tonicProfile = new HashMap<>();
        this.tonicProfile.put("mini_level_tonic", 1);
        this.tonicProfile.put("large_level_tonic", 5);
        this.tonicProfile.put("super_level_tonic", 10);
    }

    @EventHandler
    public void onStone(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        PlayerLevelData account = JustLevelAPI.getUserData(player);
        if (account == null) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (MegumiUtil.isEmpty(item)) return;

        ItemStream itemStream = ZaphkielAPI.INSTANCE.read(item);
        if (itemStream.isVanilla()) return;

        String id = itemStream.getZaphkielName();
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

    @EventHandler
    public void onTonic(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        PlayerLevelData account = JustLevelAPI.getUserData(player);
        if (account == null) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (MegumiUtil.isEmpty(item)) return;

        ItemStream itemStream = ZaphkielAPI.INSTANCE.read(item);
        if (itemStream.isVanilla()) return;

        String id = itemStream.getZaphkielName();
        if (!this.tonicProfile.containsKey(id)) return;
        e.setCancelled(true);

        if (account.isMaxLevel()) {
            player.sendMessage(ConfigFile.Prefix + "你已经满级了，请突破后使用等级补剂");
            return;
        }

        item.setAmount(item.getAmount() - 1);
        int up = this.tonicProfile.get(id);
        account.addLevel(up);
        MessageAPI.sendInformMessage(player, "&8[&6+&8] &f" + up + " &7等级提升");
    }
}
