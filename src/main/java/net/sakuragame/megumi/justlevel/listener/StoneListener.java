package net.sakuragame.megumi.justlevel.listener;

import com.taylorswiftcn.justwei.nbt.NBTItem;
import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import net.sakuragame.megumi.justlevel.file.sub.MessageFile;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import net.sakuragame.megumi.justlevel.level.TierType;
import net.sakuragame.megumi.justmessage.api.MessageAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class StoneListener implements Listener {

    private JustLevel plugin = JustLevel.getInstance();

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getItemMeta() == null) return;

        NBTItem nbtItem = new NBTItem(item);
        String id = nbtItem.getString(ConfigFile.ID_TAG);
        TierType type = TierType.getType(id);
        if (type == null) return;

        e.setCancelled(true);

        PlayerLevelData playerData = plugin.getPlayerData().get(player.getUniqueId());

        switch (type) {
            case Stage:
                if (player.isSneaking()) {
                    int i = item.getAmount();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    playerData.addStagePoints(i);
                    MessageAPI.sendActionTip(player, StringUtils.replace(MessageFile.stagePointsChange, "%points%", String.valueOf(i)));
                }
                else {
                    item.setAmount(item.getAmount() - 1);
                    playerData.addStagePoints(1);
                    MessageAPI.sendActionTip(player, StringUtils.replace(MessageFile.stagePointsChange, "%points%", "1"));
                }
                break;
            case Realm:
                if (player.isSneaking()) {
                    int i = item.getAmount();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    playerData.addRealmPoints(i);
                    MessageAPI.sendActionTip(player, StringUtils.replace(MessageFile.realmPointsChange, "%points%", String.valueOf(i)));
                }
                else {
                    item.setAmount(item.getAmount() - 1);
                    playerData.addRealmPoints(1);
                    MessageAPI.sendActionTip(player, StringUtils.replace(MessageFile.realmPointsChange, "%points%", "1"));
                }
                break;
        }
    }
}
