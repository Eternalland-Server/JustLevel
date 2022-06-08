package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.megumi.uifactory.event.comp.UIFCompSubmitEvent;
import com.taylorswiftcn.megumi.uifactory.event.screen.UIFScreenOpenEvent;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.*;
import net.sakuragame.eternal.justlevel.core.level.Realm;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justlevel.hook.ClientPlaceholder;
import net.sakuragame.eternal.justmessage.JustMessage;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

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

    @EventHandler
    public void onStageBroken(PlayerBrokenEvent.Stage e) {
        Player player = e.getPlayer();
        int stage = e.getLevel();
        MessageAPI.sendActionTip(player, "§3§l阶段提升➚§6§l(" + stage + "阶)");

        ClientPlaceholder.sendBreakRequire(player);
    }

    @EventHandler
    public void onRealmBroken(PlayerBrokenEvent.Realm e) {
        Player player = e.getPlayer();
        int level = e.getLevel();
        Realm realm = ConfigFile.realmSetting.get(level);

        MessageAPI.sendActionTip(player, "§a§l境界提升➚§6§l(" + level + "层境界)");
        JustMessage.getChatManager().sendAll("⒝ §a恭喜§7" + player.getName() + "§a突破到更高的境界!" + realm.getPrefix() + "§a!");

        ClientPlaceholder.sendBreakRequire(player);
    }

}
