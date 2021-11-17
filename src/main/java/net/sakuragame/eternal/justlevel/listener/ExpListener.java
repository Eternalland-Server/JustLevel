package net.sakuragame.eternal.justlevel.listener;

import net.sakuragame.eternal.justlevel.util.LevelUtil;
import net.sakuragame.eternal.justlevel.api.event.sub.JLPlayerExpIncreaseEvent;
import net.sakuragame.eternal.justlevel.file.sub.MessageFile;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.text.DecimalFormat;

public class ExpListener implements Listener {

    private final DecimalFormat format = new DecimalFormat("#");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExpIncrease(JLPlayerExpIncreaseEvent e) {
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
