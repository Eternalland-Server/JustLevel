package net.sakuragame.megumi.justlevel.level;

import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.event.*;
import net.sakuragame.megumi.justlevel.file.sub.ConfigFile;
import net.sakuragame.megumi.justlevel.util.LevelUtil;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public class PlayerLevelData {

    private final Player player;
    /**
     * 境界
     */
    private int realm;
    /**
     * 阶段
     */
    private int stage;
    /**
     * 等级
     */
    private int level;
    /**
     * 当前等级经验
     */
    private double exp;

    private int stagePoints;
    private int realmPoints;

    public PlayerLevelData(Player player) {
        this.player = player;
        this.stage = 1;
        this.realm = 1;
        this.level = 1;
        this.exp = 0d;
        this.stagePoints = 0;
        this.realmPoints = 0;
        this.updateExpBar();
    }

    public PlayerLevelData(Player player, int totalLevel, double currentExp, int stagePoint, int realmPoint) {
        this.player = player;
        this.stage = (totalLevel / ConfigFile.stage_level) % ConfigFile.stage_layer + 1;
        this.realm = totalLevel / ConfigFile.stage_level / ConfigFile.stage_layer + 1;
        this.level = totalLevel - (stage - 1) * ConfigFile.stage_level - (realm - 1) * ConfigFile.stage_layer * ConfigFile.stage_level + 1;
        this.exp = currentExp;
        this.stagePoints = stagePoint;
        this.realmPoints = realmPoint;
        this.updateExpBar();
    }

    public void addRealm() {
        setRealm(realm + 1);
    }

    public void addRealm(int i) {
        setRealm(realm + i);
    }

    public void addStage() {
        setStage(stage + 1);
    }

    public void addStage(int i) {
        setStage(stage + i);
    }

    public void addLevel(int upgrade) {
        JustPlayerUpgradeEvent upgradeEvent = new JustPlayerUpgradeEvent(player, level, upgrade);
        upgradeEvent.call();
        if (upgradeEvent.isCancelled()) return;
        upgrade = upgradeEvent.getUpgrade();

        int oldLevel = level;
        setLevel(level + upgrade);

        IEvent upgradedEvent = new JustPlayerUpgradedEvent(player, oldLevel, level);
        upgradedEvent.call();
    }

    public void setRealm(int realm) {
        int oldRealm = this.realm;
        this.realm = Math.min(ConfigFile.realm_layer, Math.max(0, realm));

        IEvent event = new JustPlayerRealmChangeEvent(player, oldRealm, this.realm);
        event.call();
    }

    public void setStage(int stage) {
        int oldStage = this.stage;
        this.stage = Math.min(ConfigFile.stage_layer, Math.max(0, stage));

        IEvent event = new JustPlayerStageChangeEvent(player, oldStage, this.stage, realm);
        event.call();
    }

    public void setLevel(int level) {
        this.level = Math.min(ConfigFile.stage_level, level);
        updateLevelTip();
    }

    public void setExp(double exp) {
        double upgradeRequire = getUpgradeExp();
        double value = Math.min(upgradeRequire, exp);
        if (value == upgradeRequire) {
            level++;
            this.exp = 0;
            return;
        }
        this.exp = value;
    }

    public void save() {
        int totalLevel = getTotalLevel();
        JustLevel.getInstance().getStorageManager().updatePlayerData(player, totalLevel, exp, stagePoints, realmPoints);
    }

    public int getTotalLevel() {
        return level + (stage - 1) * ConfigFile.stage_level + (realm - 1) * ConfigFile.stage_layer * ConfigFile.stage_level - 1;
    }

    public void addExp(double value) {
        if (realm == ConfigFile.realm_layer) return;
        if (level == ConfigFile.stage_level) return;

        JustPlayerExpChangeEvent changeEvent = new JustPlayerExpChangeEvent(player, level, exp, value);
        changeEvent.call();
        if (changeEvent.isCancelled()) return;
        value = changeEvent.getExpChange();

        double experience = value + exp;
        int upgrade = 0;
        setExp(0);
        while (experience > 0) {
            double upgradeExp = LevelUtil.getUpgradeRequireExp(level + upgrade);
            if (this.level + upgrade == ConfigFile.stage_level) {
                setExp(upgradeExp);
                break;
            }

            if (experience >= upgradeExp) {
                experience -= upgradeExp;
                upgrade++;
            }
            else {
                setExp(experience);
                experience = 0;
            }
        }

        if (upgrade != 0) {
            addLevel(upgrade);
        }
        IEvent expChangedEvent = new JustPlayerExpChangedEvent(player, level, exp, value);
        expChangedEvent.call();

        updateExpBar();
    }

    public void addStagePoints(int points) {
        JustPlayerIncreasePointsEvent pointsEvent = new JustPlayerIncreasePointsEvent(player, TierType.Stage, points);
        pointsEvent.call();
        if (pointsEvent.isCancelled()) return;
        points = pointsEvent.getPoints();

        setStagePoints(getStagePoints() + points);

        IEvent event = new JustPlayerIncreasedPointsEvent(player, TierType.Stage, points);
        event.call();
    }

    public void addRealmPoints(int points) {
        JustPlayerIncreasePointsEvent pointsEvent = new JustPlayerIncreasePointsEvent(player, TierType.Realm, points);
        pointsEvent.call();
        if (pointsEvent.isCancelled()) return;
        points = pointsEvent.getPoints();

        setRealmPoints(getRealmPoints() + points);

        IEvent event = new JustPlayerIncreasedPointsEvent(player, TierType.Realm, points);
        event.call();
    }

    public void takeStagePoints(int points) {
        setStagePoints(Math.max(0, getStagePoints() - points));
    }

    public void takeRealmPoints(int points) {
        setRealmPoints(Math.max(0, getRealmPoints() - points));
    }

    public void setStagePoints(int points) {
        this.stagePoints = Math.max(0, points);
    }

    public void setRealmPoints(int points) {
        this.realmPoints = Math.max(0, points);
    }

    public void updateExpBar() {
        this.player.setExp((float) (this.exp / getUpgradeExp()));
    }

    public void updateLevelTip() {
        this.player.setLevel(this.level);
    }

    public double getUpgradeExp() {
        return LevelUtil.getUpgradeRequireExp(this.level);
    }
}
