package net.sakuragame.eternal.justlevel.level;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.*;
import net.sakuragame.eternal.justlevel.api.event.sub.*;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import net.sakuragame.eternal.justlevel.hook.DragonCoreSync;
import net.sakuragame.eternal.justlevel.util.LevelUtil;
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
        this.updateLevelTip();
    }

    public void syncPlaceHolder() {
        DragonCoreSync.send(player, exp, getUpgradeExp(), stage, getRealmName(), stagePoints, realmPoints);
    }

    public void syncExp() {
        DragonCoreSync.sendExp(player, exp, getUpgradeExp());
    }

    public void syncCurrentExp() {
        DragonCoreSync.send(player, DragonCoreSync.PlaceHolder.CURRENT_EXP, exp);
    }

    public void syncStage() {
        DragonCoreSync.send(player, DragonCoreSync.PlaceHolder.CURRENT_STAGE, stage);
    }

    public void syncRealm() {
        DragonCoreSync.send(player, DragonCoreSync.PlaceHolder.CURRENT_REALM, getRealmName());
    }

    public void syncStagePoints() {
        DragonCoreSync.send(player, DragonCoreSync.PlaceHolder.STAGE_POINTS, stagePoints);
    }

    public void syncRealmPoints() {
        DragonCoreSync.send(player, DragonCoreSync.PlaceHolder.REALM_POINTS, realmPoints);
    }

    public void syncBreakRequire() {
        RealmSetting setting = ConfigFile.realmSetting.get(realm);
        int stageRequire = canStageBreak() ? 1 : 0;
        int realmRequire = canRealmBreak() ? 1 : 0;
        boolean stageA = (level >= ConfigFile.stage_level);
        boolean stageB = (stagePoints >= setting.getStageConsume());
        boolean stageC = (JustLevel.getInstance().getEconomy().getBalance(player) >= setting.getStageBreakPrice());
        boolean realmA = (stage >= ConfigFile.stage_layer);
        boolean realmB = (realmPoints >= setting.getRealmConsume());
        boolean realmC = (JustLevel.getInstance().getEconomy().getBalance(player) >= setting.getRealmBreakPrice());
        DragonCoreSync.sendRequire(
                player,
                stageRequire,
                realmRequire,
                String.format("%s &f等级: %s", stageA ? "┦" : "┧", stageA ? "&a" + ConfigFile.stage_level : "&c" + ConfigFile.stage_level),
                String.format("%s &f突破石: %s", stageB ? "┦" : "┧", stageB ? "&a" + setting.getStageConsume() : "&c" + setting.getStageConsume()),
                String.format("%s &f金币: %s", stageC ? "┦" : "┧", stageC ? "&a" + setting.getStageBreakPrice() : "&c" + setting.getStageBreakPrice()),
                String.format("%s &f阶段: %s", realmA ? "┦" : "┧", realmA ? "&a" + setting.getStageConsume() : "&c" + setting.getStageConsume()),
                String.format("%s &f境界石: %s", realmB ? "┦" : "┧", realmB ? "&a" + setting.getStageConsume() : "&c" + setting.getStageConsume()),
                String.format("%s &f金币: %s", realmC ? "┦" : "┧", realmC ? "&a" + setting.getRealmBreakPrice() : "&c" + setting.getRealmBreakPrice())
        );
    }

    public boolean canStageBreak() {
        if (stage == ConfigFile.stage_layer) return false;
        RealmSetting setting = ConfigFile.realmSetting.get(realm);
        return level == ConfigFile.stage_level && stagePoints >= setting.getStageConsume() && JustLevel.getInstance().getEconomy().getBalance(player) >= setting.getStageBreakPrice();
    }

    public boolean canRealmBreak() {
        if (realm == ConfigFile.realm_layer) return false;
        RealmSetting setting = ConfigFile.realmSetting.get(realm);
        return level == ConfigFile.stage_level && stage == ConfigFile.stage_layer && realmPoints >= setting.getRealmConsume() && JustLevel.getInstance().getEconomy().getBalance(player) >= setting.getRealmBreakPrice();
    }

    public int getStageBreakPoints() {
        return ConfigFile.realmSetting.get(realm).getStageConsume();
    }

    public int getRealmBreakPoints() {
        return ConfigFile.realmSetting.get(realm).getRealmConsume();
    }

    public int getStageBreakPrice() {
        return ConfigFile.realmSetting.get(realm).getStageBreakPrice();
    }

    public int getRealmBreakPrice() {
        return ConfigFile.realmSetting.get(realm).getRealmBreakPrice();
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

    public void setRealm(int realm) {
        int oldRealm = this.realm;
        this.realm = Math.min(ConfigFile.realm_layer, Math.max(0, realm));

        JustLevelEvent event = new JustPlayerRealmChangeEvent(player, oldRealm, this.realm);
        event.call();

        syncRealm();
    }

    public void setStage(int stage) {
        int oldStage = this.stage;
        this.stage = Math.min(ConfigFile.stage_layer, Math.max(0, stage));

        JustLevelEvent event = new JustPlayerStageChangeEvent(player, oldStage, this.stage, realm);
        event.call();

        syncStage();
    }

    public void addLevel(int upgrade) {
        JustPlayerUpgradeEvent upgradeEvent = new JustPlayerUpgradeEvent(player, level, upgrade);
        upgradeEvent.call();
        if (upgradeEvent.isCancelled()) return;
        upgrade = upgradeEvent.getUpgrade();

        int oldLevel = level;
        setLevel(level + upgrade);

        JustLevelEvent upgradedEvent = new JustPlayerUpgradedEvent(player, oldLevel, level);
        upgradedEvent.call();
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

        syncCurrentExp();
    }

    public void addExp(double value) {
        if (realm == ConfigFile.realm_layer && stage == ConfigFile.stage_layer && level == ConfigFile.stage_level) return;
        if (level == ConfigFile.stage_level && exp == getUpgradeExp()) return;

        JustPlayerExpIncreaseEvent increaseEvent = new JustPlayerExpIncreaseEvent(player, level, exp, value);
        increaseEvent.call();
        if (increaseEvent.isCancelled()) return;
        value = increaseEvent.getIncrease();

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
        JustLevelEvent increasedEvent = new JustPlayerExpIncreasedEvent(player, level, exp, value);
        increasedEvent.call();

        updateExpBar();
        syncExp();
    }

    public void addStagePoints(int points) {
        JustPlayerIncreasePointsEvent pointsEvent = new JustPlayerIncreasePointsEvent(player, LevelDefine.Stage, points);
        pointsEvent.call();
        if (pointsEvent.isCancelled()) return;
        points = pointsEvent.getPoints();

        setStagePoints(getStagePoints() + points);

        JustLevelEvent event = new JustPlayerIncreasedPointsEvent(player, LevelDefine.Stage, points);
        event.call();
    }

    public void addRealmPoints(int points) {
        JustPlayerIncreasePointsEvent pointsEvent = new JustPlayerIncreasePointsEvent(player, LevelDefine.Realm, points);
        pointsEvent.call();
        if (pointsEvent.isCancelled()) return;
        points = pointsEvent.getPoints();

        setRealmPoints(getRealmPoints() + points);

        JustLevelEvent event = new JustPlayerIncreasedPointsEvent(player, LevelDefine.Realm, points);
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

        syncStagePoints();
    }

    public void setRealmPoints(int points) {
        this.realmPoints = Math.max(0, points);

        syncRealmPoints();
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

    public String getRealmName() {
        return ConfigFile.realmSetting.get(realm).getName();
    }

    public void save() {
        int totalLevel = getTotalLevel();
        JustLevel.getInstance().getStorageManager().updatePlayerData(player, totalLevel, exp, stagePoints, realmPoints);
    }

    public int getTotalLevel() {
        return level + ((stage - 1) + (realm - 1) * ConfigFile.stage_layer) * ConfigFile.stage_level - 1;
    }

    public int getTotalStage() {
        return stage + (realm - 1) * ConfigFile.stage_layer;
    }
}
