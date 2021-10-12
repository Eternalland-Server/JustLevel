package net.sakuragame.megumi.justlevel.listener;

import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import net.sakuragame.megumi.justlevel.level.PlayerLevelData;
import de.tr7zw.changeme.nbtapi.NBTItem;
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
        if (e.getAction() != Action.RIGHT_CLICK_AIR || e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getItemMeta() == null) return;

        NBTItem nbtItem = new NBTItem(item);
        String id = nbtItem.getString(ConfigFile.ID_TAG);

        PlayerLevelData playerData = plugin.getPlayerData().get(player.getUniqueId());

        switch (id) {
            case "stage":
                if (player.isSneaking()) {
                    int i = item.getAmount();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    playerData.addStagePoint(i);
                    player.sendMessage("成功消耗全部stage");
                }
                else {
                    item.setAmount(item.getAmount() - 1);
                    playerData.addStagePoint(1);
                    player.sendMessage("成功消耗一个stage");
                }
                break;
            case "realm":
                if (player.isSneaking()) {
                    int i = item.getAmount();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                    playerData.addRealmPoint(i);
                    player.sendMessage("成功消耗全部realm");
                }
                else {
                    item.setAmount(item.getAmount() - 1);
                    playerData.addRealmPoint(1);
                    player.sendMessage("成功消耗一个realm");
                }
                break;
        }
    }
}
