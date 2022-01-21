package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.megumi.uifactory.event.comp.UIFCompSubmitEvent;
import com.taylorswiftcn.megumi.uifactory.event.screen.UIFScreenOpenEvent;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.api.event.*;
import net.sakuragame.eternal.justlevel.hook.DragonSync;
import net.sakuragame.eternal.justmessage.api.MessageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class UIListener implements Listener {

    @EventHandler
    public void onOpen(UIFScreenOpenEvent e) {
        Player player = e.getPlayer();
        String screenID = e.getScreenID();

        if (!screenID.equals("player_breakThough")) return;

        DragonSync.sendBreakRequire(player);
    }

    @EventHandler
    public void onExpChange(PlayerExpChangeEvent e) {
        DragonSync.sendExp(e.getPlayer());
    }

    @EventHandler
    public void onRealmChange(PlayerRealmChangeEvent e) {
        DragonSync.sendLevel(e.getPlayer());
    }

    @EventHandler
    public void onStageChange(PlayerStageChangeEvent e) {
        DragonSync.sendLevel(e.getPlayer());
    }

    @EventHandler
    public void onPointsChange(PlayerPointChangeEvent e) {
        DragonSync.sendPoints(e.getPlayer());
    }

    @EventHandler
    public void onBreakButtonClick(UIFCompSubmitEvent e) {
        Player player = e.getPlayer();

        String plugin = e.getParams().getParam(0);
        if (!plugin.equals(JustLevel.getInstance().getName())) return;

        int index = e.getParams().getParamI(1);

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
        int stage = e.getStage();
        MessageAPI.sendActionTip(player, "§3§l阶段提升➚§6§l(" + stage + ")");

        DragonSync.sendBreakRequire(player);
    }

    @EventHandler
    public void onRealmBroken(PlayerBrokenEvent.Realm e) {
        Player player = e.getPlayer();
        int realm = e.getRealm();
        MessageAPI.sendActionTip(player, "§a§l境界提升➚§6§l(" + realm + ")");

        DragonSync.sendBreakRequire(player);
    }

}