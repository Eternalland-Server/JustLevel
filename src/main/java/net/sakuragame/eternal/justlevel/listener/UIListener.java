package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.megumi.uifactory.event.comp.UIFCompSubmitEvent;
import com.taylorswiftcn.megumi.uifactory.event.screen.UIFScreenOpenEvent;
import net.sakuragame.eternal.dragoncore.network.PacketSender;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.*;
import net.sakuragame.eternal.justlevel.core.level.Realm;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justlevel.hook.ClientPlaceholder;
import net.sakuragame.eternal.justmessage.JustMessage;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class UIListener implements Listener {

    @EventHandler
    public void onOpen(UIFScreenOpenEvent e) {
        Player player = e.getPlayer();
        String screenID = e.getScreenID();

        if (!screenID.equals("user_level")) return;

        ClientPlaceholder.sendBreakRequire(player);
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e) {
        ClientPlaceholder.sendExp(e.getPlayer());
    }

    @EventHandler
    public void onRealmChange(PlayerRealmChangeEvent e) {
        ClientPlaceholder.sendLevel(e.getPlayer());
    }

    @EventHandler
    public void onStageChange(PlayerStageChangeEvent e) {
        ClientPlaceholder.sendLevel(e.getPlayer());
    }

    @EventHandler
    public void onPointsChange(PlayerPointChangeEvent e) {
        ClientPlaceholder.sendPoints(e.getPlayer());
    }

    @EventHandler
    public void onBreakButtonClick(UIFCompSubmitEvent e) {
        Player player = e.getPlayer();

        if (!e.getScreenID().equals("user_level")) return;
        if (!e.getCompID().equals("break")) return;

        int index = e.getParams().getParamI(0);

        if (index == 0) {
            JustLevelAPI.tryBreakStage(player);
            return;
        }

        if (index == 1) {
            JustLevelAPI.tryBreakRealm(player);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStageBroken(PlayerBrokenEvent.Stage e) {
        Player player = e.getPlayer();
        int stage = e.getLevel();

        e.addMessage("&a&l破功伤害➚&8&l[ &e&l+500 &8&l]");
        e.addMessage("&a&l阶段提升➚&8&l[ &e&l" + stage + "阶 &8&l]");

        PacketSender.sendPlaySound(player, "sounds/l/0.ogg", 0.5f, 1, false, 0, 0, 0);

        ClientPlaceholder.sendBreakRequire(player);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRealmBroken(PlayerBrokenEvent.Realm e) {
        Player player = e.getPlayer();
        int level = e.getLevel();
        Realm realm = ConfigFile.realmSetting.get(level);

        e.addMessage("&a&l破功伤害➚&8&l[ &e&l+20000 &8&l]");
        e.addMessage("&a&l境界提升➚&8&l[ &e&l" + level + "层 &8&l]");

        PacketSender.sendPlaySound(player, "sounds/l/1.ogg", 0.5f, 1, false, 0, 0, 0);

        JustMessage.getChatManager().sendAll("⒝ §a恭喜§7" + player.getName() + "§a突破到更高的境界!" + realm.getPrefix() + "§a!");

        ClientPlaceholder.sendBreakRequire(player);
    }

}
