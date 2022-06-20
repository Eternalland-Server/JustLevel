package net.sakuragame.eternal.justlevel.listener;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.PlayerExpIncreaseEvent;
import net.sakuragame.eternal.justlevel.api.event.PlayerLevelChangeEvent;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import net.sakuragame.eternal.justlevel.util.Utils;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
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

        MessageAPI.sendActionTip(player,
                MessageFile.expChange
                        .replace("%exp%", a.format(total))
                        .replace("%multiple%", b.format(total / amount))
        );
    }

    @EventHandler
    public void onLevelChange(PlayerLevelChangeEvent e) {
        Player player = e.getPlayer();
        int level = e.getNewLevel();
        if (level != 200) return;

        player.sendTitle("", "§3§l200级已达成 §8§l| §3§l境界突破已开启", 10, 60, 10);
    }
}
