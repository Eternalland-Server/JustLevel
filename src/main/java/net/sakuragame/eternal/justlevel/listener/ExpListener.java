package net.sakuragame.eternal.justlevel.listener;

import net.sakuragame.eternal.justlevel.api.event.PlayerExpIncreaseEvent;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import net.sakuragame.eternal.justlevel.util.Utils;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;

public class ExpListener implements Listener {

    private final DecimalFormat format = new DecimalFormat("#");

    @EventHandler
    public void onExpIncrease(PlayerExpIncreaseEvent.Pre e) {
        Player player = e.getPlayer();
        double amount = e.getAmount();
        double addition = Utils.getExpAddition(player, amount);

        e.addAddition(addition);
    }

    @EventHandler
    public void onExpChange(PlayerExpIncreaseEvent.Post e) {
        Player player = e.getPlayer();
        double amount = e.getAmount();
        double addition = e.getAddition();

        MessageAPI.sendActionTip(player,
                MessageFile.expChange
                        .replace("%exp%", format.format(amount))
                        .replace("%addition%", format.format(addition))
        );
    }
}
