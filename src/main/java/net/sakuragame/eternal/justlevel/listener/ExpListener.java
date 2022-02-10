package net.sakuragame.eternal.justlevel.listener;

import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.PlayerExpIncreaseEvent;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import net.sakuragame.eternal.justlevel.util.Utils;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

        double value = amount * (support + card);

        e.addAddition(value);
    }

    @EventHandler
    public void onExpChange(PlayerExpIncreaseEvent.Post e) {
        Player player = e.getPlayer();
        double amount = e.getAmount();
        double addition = e.getAddition();
        double total = amount + addition;

        MessageAPI.sendActionTip(player,
                MessageFile.expChange
                        .replace("%exp%", a.format(total))
                        .replace("%multiple%", b.format(amount / total))
        );
    }
}
