package net.sakuragame.eternal.justlevel.listener;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.PlayerExpIncreaseEvent;
import net.sakuragame.eternal.justlevel.api.event.PlayerLevelChangeEvent;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import net.sakuragame.eternal.justlevel.util.Utils;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;

public class ExpListener implements Listener {

    private final DecimalFormat a = new DecimalFormat("#");
    private final DecimalFormat b = new DecimalFormat("#.#");

    @EventHandler
    public void onExpIncrease(PlayerExpIncreaseEvent.Pre e) {
        Player player = e.getPlayer();
        double amount = e.getAmount();
        double support = Utils.getExpAddition(player);
        double card = JustLevelAPI.getCardAddition(player);
        double system = JustLevel.getMultiExpManager().isValid() ? JustLevel.getMultiExpManager().getAddition() : 0;

        double value = amount * (support + card + system);

        e.addAddition(value);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onExpChange(PlayerExpIncreaseEvent.Post e) {
        Player player = e.getPlayer();
        double amount = e.getAmount();
        double addition = e.getAddition();
        double total = amount + addition;

        PlayerLevelData account = JustLevelAPI.getUserData(player.getUniqueId());

        MessageAPI.sendInformMessage(player, "§8[§e+§8] §e" + a.format(total) + "EXP §c(x" + b.format(total / amount) + ")" + (account.isMaxLevel() ? " §a[可突破]" : ""));
        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.33f, 1);
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent e) {
        Player player = e.getPlayer();
        int pre = e.getOldLevel();
        int post = e.getNewLevel();
        int change = post - pre;

        String s = (change > 0 ? "§e+" : "§c") + change;
        if (post != 200) {
            player.sendTitle("", "§a§l等级提升(" + s + "§a§l) §8§l[§eLv." + pre + "§8§l] §3§l➜ §8§l[§eLv." + post + "§8§l]", 0, 60, 0);
        }
        else {
            player.sendTitle("", "§3§l200级已达成 §b§l| §3§l境界突破已开启", 10, 60, 10);
            player.sendMessage(ConfigFile.Prefix + "§7境界突破已开启,按§aE§7进入角色界面进行突破");
        }
    }
}
