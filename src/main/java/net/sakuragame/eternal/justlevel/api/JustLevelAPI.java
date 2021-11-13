package net.sakuragame.eternal.justlevel.api;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.JustLevelEvent;
import net.sakuragame.eternal.justlevel.api.event.sub.JustPlayerRealmBrokenEvent;
import net.sakuragame.eternal.justlevel.api.event.sub.JustPlayerStageBrokenEvent;
import net.sakuragame.eternal.justlevel.level.PlayerLevelData;
import net.sakuragame.eternal.justlevel.level.RealmSetting;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.entity.Player;

public class JustLevelAPI {

    private final static JustLevel plugin = JustLevel.getInstance();

    public static PlayerLevelData getData(Player player) {
        return plugin.getPlayerData().get(player.getUniqueId());
    }

    public static int getRealm(Player player) {
        return getData(player).getRealm();
    }

    public static int getStage(Player player) {
        return getData(player).getStage();
    }

    public static int getLevel(Player player) {
        return getData(player).getLevel();
    }

    public static int getTotalStage(Player player) {
        return getData(player).getTotalStage();
    }

    public static void addExp(Player player, double exp) {
        plugin.getPlayerData().get(player.getUniqueId()).addExp(exp);
    }

    public static RealmSetting getRealmSetting(int realm) {
        return ConfigFile.realmSetting.get(realm);
    }

    public static void tryBreakStage(Player player) {
        PlayerLevelData data = plugin.getPlayerData().get(player.getUniqueId());

        if (!data.canStageBreak()) {
            return;
        }

        RealmSetting setting = ConfigFile.realmSetting.get(data.getRealm());

        data.setLevel(1);
        data.setExp(0);
        data.addStage();
        data.takeStagePoints(setting.getStageConsume());
        data.syncBreakRequire();
        plugin.getEconomy().withdrawPlayer(player, setting.getStageBreakPrice());

        JustLevelEvent event = new JustPlayerStageBrokenEvent(player, data.getStage(), setting.getStageConsume(), setting.getStageBreakPrice());
        event.call();
    }

    public static void tryBreakRealm(Player player) {
        PlayerLevelData data = plugin.getPlayerData().get(player.getUniqueId());

        if (!data.canRealmBreak()) {
            return;
        }

        RealmSetting setting = ConfigFile.realmSetting.get(data.getRealm());

        data.setLevel(1);
        data.setExp(0);
        data.setStage(1);
        data.addRealm();
        data.takeRealmPoints(setting.getRealmConsume());
        data.syncBreakRequire();
        plugin.getEconomy().withdrawPlayer(player, setting.getRealmBreakPrice());

        JustLevelEvent event = new JustPlayerRealmBrokenEvent(player, data.getRealm(), setting.getRealmConsume(), setting.getRealmBreakPrice());
        event.call();
    }

}