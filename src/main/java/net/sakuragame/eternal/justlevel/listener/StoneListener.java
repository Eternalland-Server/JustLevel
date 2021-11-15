package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.justwei.util.MegumiUtil;
import ink.ptms.zaphkiel.ZaphkielAPI;
import ink.ptms.zaphkiel.api.ItemStream;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTag;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTagData;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.level.LevelDefine;
import net.sakuragame.eternal.justlevel.level.PlayerLevelData;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class StoneListener implements Listener {

    private final JustLevel plugin = JustLevel.getInstance();

    @EventHandler
    public void onRight(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (!(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (MegumiUtil.isEmpty(item)) return;

        ItemStream itemStream = ZaphkielAPI.INSTANCE.read(item);
        if (itemStream.isVanilla()) return;

        ItemTag itemTag = itemStream.getZaphkielData();
        ItemTagData data = itemTag.getDeep("eternal.ident");
        if (data == null) return;

        String ident = data.asString();

        e.setCancelled(true);

        PlayerLevelData playerData = plugin.getPlayerData().get(player.getUniqueId());

        switch (ident) {
            case "stage_stone":
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
            case "realm_stone":
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
