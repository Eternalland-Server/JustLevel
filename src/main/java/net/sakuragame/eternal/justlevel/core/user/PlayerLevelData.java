package net.sakuragame.eternal.justlevel.core.user;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.*;
import net.sakuragame.eternal.justlevel.core.AutoSave;
import net.sakuragame.eternal.justlevel.core.level.Define;
import net.sakuragame.eternal.justlevel.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerLevelData extends LevelAccount {

    private final int tid;
    private double dailyExpLimit;

    public PlayerLevelData(UUID uuid) {
        super(uuid);
        this.tid = new AutoSave(uuid).runTaskTimerAsynchronously(JustLevel.getInstance(), 6000, 6000).getTaskId();
        this.dailyExpLimit = 0;
    }

    public PlayerLevelData(UUID uuid, int realm, int stage, int level, double exp, int stagePoints, int realmPoints) {
        super(uuid, realm, stage, level, exp, stagePoints, realmPoints);
        this.tid = new AutoSave(uuid).runTaskTimerAsynchronously(JustLevel.getInstance(), 6000, 6000).getTaskId();
        this.dailyExpLimit = 0;
    }

    public void addDailyExpLimit(double value) {
        this.dailyExpLimit = Math.min(this.dailyExpLimit + value, Define.Daily.getMax());
    }

    public double getDailyExpLimit() {
        return dailyExpLimit;
    }

    public void setDailyExpLimit(double dailyExpLimit) {
        this.dailyExpLimit = dailyExpLimit;
    }

    @Override
    public void setRealm(int realm) {
        if (realm == this.getRealm()) return;

        super.setRealm(realm);
        PlayerRealmChangeEvent event = new PlayerRealmChangeEvent(this.getBukkitPlayer(), this.getRealm());
        event.call();
    }

    @Override
    public void setStage(int stage) {
        if (stage == this.getStage()) return;

        super.setStage(stage);
        PlayerStageChangeEvent event = new PlayerStageChangeEvent(this.getBukkitPlayer(), this.getStage());
        event.call();
    }

    @Override
    public void setLevel(int level) {
        if (level == this.getLevel()) return;

        int old = this.getLevel();
        super.setLevel(level);
        PlayerLevelChangeEvent event = new PlayerLevelChangeEvent(this.getBukkitPlayer(), old, level);
        event.call();
    }

    @Override
    public void setExp(double exp) {
        super.setExp(exp);
        PlayerExpChangeEvent event = new PlayerExpChangeEvent(this.getBukkitPlayer(), this.getExp());
        event.call();
    }

    @Override
    public void setRealmPoints(int realmPoints) {
        super.setRealmPoints(realmPoints);
        PlayerPointChangeEvent.Realm event = new PlayerPointChangeEvent.Realm(this.getBukkitPlayer(), this.getRealmPoints());
        event.call();
    }

    @Override
    public void setStagePoints(int stagePoints) {
        super.setStagePoints(stagePoints);
        PlayerPointChangeEvent.Stage event = new PlayerPointChangeEvent.Stage(this.getBukkitPlayer(), this.getStagePoints());
        event.call();
    }

    @Override
    public void addLevel(int value) {
        int oldLevel = this.getLevel();
        int newLevel = oldLevel + value;

        this.setLevel(newLevel);
    }

    @Override
    public void addExp(double value) {
        /*if (this.canBreakRealm()) return;
        if (this.canBreakStage()) return;*/

        Player player = this.getBukkitPlayer();

        PlayerExpIncreaseEvent.Pre preEvent = new PlayerExpIncreaseEvent.Pre(player, value);
        preEvent.call();
        if (preEvent.isCancelled()) return;

        int levelUP = 0;
        double addition = preEvent.getAddition();
        double expPool = value + addition;
        double limit = Define.Daily.getMax() - this.dailyExpLimit;

        if (limit < expPool) {
            expPool = limit;
            this.dailyExpLimit = Define.Daily.getMax();
        }
        else {
            this.addDailyExpLimit(expPool);
        }

        if (expPool != 0) {
            expPool += this.getExp();
            this.setExp(0);
        }

        while (expPool > 0) {
            double upgradeExp = Utils.getUpgradeRequireExp(this.getRealm(), this.getStage(), this.getLevel() + levelUP);

            if (this.getLevel() + levelUP >= Define.Level.getMax()) {
                this.setExp(upgradeExp);
                break;
            }

            if (expPool >= upgradeExp) {
                expPool -= upgradeExp;
                levelUP++;
                continue;
            }

            this.setExp(expPool);
            break;
        }

        if (levelUP != 0) {
            this.addLevel(levelUP);
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.33f, 1);
        }

        PlayerExpIncreaseEvent.Post postEvent = new PlayerExpIncreaseEvent.Post(player, value, addition);
        postEvent.call();

        updateVanillaExp();
    }

    @Override
    public void saveData() {
        JustLevel.getStorageManager().saveData(
                this.getUUID(),
                this.getRealm(),
                this.getStage(),
                this.getLevel(),
                this.getExp(),
                this.getRealmPoints(),
                this.getStagePoints()
        );

        JustLevel.getStorageManager().setDailyExp(this.getUUID(), this.dailyExpLimit);
    }

    public void cancelAutoSave() {
        Bukkit.getScheduler().cancelTask(this.tid);
    }
}
