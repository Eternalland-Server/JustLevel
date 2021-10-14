package net.sakuragame.megumi.justlevel.listener;

import net.sakuragame.megumi.justlevel.event.JustPlayerExpIncreaseEvent;
import net.sakuragame.megumi.justlevel.file.sub.MessageFile;
import net.sakuragame.megumi.justlevel.util.LevelUtil;
import net.sakuragame.megumi.justmessage.api.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;

public class ExpListener implements Listener {

    private final DecimalFormat format = new DecimalFormat("#");

    @EventHandler
    public void onExpIncrease(JustPlayerExpIncreaseEvent e) {
        Player player = e.getPlayer();
        double increase = e.getIncrease();
        double addition = LevelUtil.getPlayerExpAddition(player, increase);

        e.setIncrease(increase + addition);
        MessageAPI.sendActionTip(player,
                MessageFile.expChange
                        .replace("%exp%", format.format(increase))
                        .replace("%addition%", format.format(addition))
        );
    }
}
