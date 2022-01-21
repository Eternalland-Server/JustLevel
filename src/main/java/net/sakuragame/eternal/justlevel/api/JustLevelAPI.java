package net.sakuragame.eternal.justlevel.api;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.core.Define;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import org.bukkit.entity.Player;

import java.util.UUID;

public class JustLevelAPI {

    public static PlayerLevelData getUserData(Player player) {
        return getUserData(player.getUniqueId());
    }

    public static PlayerLevelData getUserData(UUID uuid) {
        return JustLevel.getUserManager().getAccount(uuid);
    }

    public static int getRealm(Player player) {
        return getRealm(player.getUniqueId());
    }

    public static int getRealm(UUID uuid) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return -1;
        return account.getRealm();
    }

    public static int getStage(Player player) {
        return getStage(player.getUniqueId());
    }

    public static int getStage(UUID uuid) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return -1;
        return account.getStage();
    }

    public static int getTotalStage(Player player) {
        return getTotalStage(player.getUniqueId());
    }

    public static int getTotalStage(UUID uuid) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return -1;

        return (account.getRealm() - 1) * Define.Stage.getMax() + account.getStage();
    }

    public static int getLevel(Player player) {
        return getLevel(player.getUniqueId());
    }

    public static int getLevel(UUID uuid) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return -1;
        return account.getLevel();
    }

    public static double getExp(Player player) {
        return getExp(player.getUniqueId());
    }

    public static double getExp(UUID uuid) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return -1;
        return account.getExp();
    }

    public static double getUpgradeExp(Player player) {
        return getUpgradeExp(player.getUniqueId());
    }

    public static double getUpgradeExp(UUID uuid) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return -1;
        return account.getUpgradeExp();
    }

    public static int getRealmPoints(Player player) {
        return getRealmPoints(player.getUniqueId());
    }

    public static int getRealmPoints(UUID uuid) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return -1;
        return account.getRealmPoints();
    }

    public static int getStagePoints(Player player) {
        return getStagePoints(player.getUniqueId());
    }

    public static int getStagePoints(UUID uuid) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return -1;
        return account.getStagePoints();
    }

    public static String getRealmPrefix(Player player) {
        return getRealmPrefix(player.getUniqueId());
    }

    public static String getRealmPrefix(UUID uuid) {
        return JustLevel.getUserManager().getRealmPrefix(uuid);
    }

    public static String getRealmName(Player player) {
        return getRealmName(player.getUniqueId());
    }

    public static String getRealmName(UUID uuid) {
        return JustLevel.getUserManager().getRealmName(uuid);
    }

    public static void setRealm(Player player, int realm) {
        setRealm(player.getUniqueId(), realm);
    }

    public static void setRealm(UUID uuid, int realm) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.setRealm(realm);
    }

    public static void setStage(Player player, int stage) {
        setStage(player.getUniqueId(), stage);
    }

    public static void setStage(UUID uuid, int stage) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.setStage(stage);
    }

    public static void setLevel(Player player, int level) {
        setLevel(player.getUniqueId(), level);
    }

    public static void setLevel(UUID uuid, int level) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.setLevel(level);
    }

    public static void setExp(Player player, double exp) {
        setExp(player.getUniqueId(), exp);
    }

    public static void setExp(UUID uuid, double exp) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.setExp(exp);
    }

    public static void setRealmPoints(Player player, int i) {
        setRealmPoints(player.getUniqueId(), i);
    }

    public static void setRealmPoints(UUID uuid, int i) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.setRealmPoints(i);
    }

    public static void setStagePoints(Player player, int i) {
        setStagePoints(player.getUniqueId(), i);
    }

    public static void setStagePoints(UUID uuid, int i) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.setStagePoints(i);
    }

    public static void addExp(Player player, double exp) {
        addExp(player.getUniqueId(), exp);
    }

    public static void addExp(UUID uuid, double exp) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.addExp(exp);
    }

    public static void addRealmPoints(Player player, int i) {
        addRealmPoints(player.getUniqueId(), i);
    }

    public static void addRealmPoints(UUID uuid, int i) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.addRealmPoints(i);
    }

    public static void addStagePoints(Player player, int i) {
        addStagePoints(player.getUniqueId(), i);
    }

    public static void addStagePoints(UUID uuid, int i) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.addStagePoints(i);
    }

    public static void takeRealmPoints(Player player, int i) {
        takeRealmPoints(player.getUniqueId(), i);
    }

    public static void takeRealmPoints(UUID uuid, int i) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.takeRealmPoints(i);
    }

    public static void takeStagePoints(Player player, int i) {
        takeStagePoints(player.getUniqueId(), i);
    }

    public static void takeStagePoints(UUID uuid, int i) {
        PlayerLevelData account = getUserData(uuid);
        if (account == null) return;
        account.takeStagePoints(i);
    }

    public static boolean tryBreakRealm(Player player) {
        return JustLevel.getUserManager().tryBreakRealm(player);
    }

    public static boolean tryBreakStage(Player player) {
        return JustLevel.getUserManager().tryBreakStage(player);
    }

}
