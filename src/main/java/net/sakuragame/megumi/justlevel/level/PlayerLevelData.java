package net.sakuragame.megumi.justlevel.level;

import com.taylorswiftcn.justwei.event.IEvent;
import net.sakuragame.megumi.justlevel.JustLevel;
import net.sakuragame.megumi.justlevel.event.JustPlayerExpChangeEvent;
import net.sakuragame.megumi.justlevel.event.JustPlayerExpChangedEvent;
import net.sakuragame.megumi.justlevel.event.JustPlayerUpgradeEvent;
import net.sakuragame.megumi.justlevel.event.JustPlayerUpgradedEvent;
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

    private int stagePoint;
    private int realmPoint;

    public PlayerLevelData(Player player) {
        this.player = player;
        this.stage = 1;
        this.realm = 1;
        this.level = 1;
        this.exp = 0d;
        this.stagePoint = 0;
        this.realmPoint = 0;
        this.updateExpBar();
    }

    public PlayerLevelData(Player player, int totalLevel, double currentExp, int stagePoint, int realmPoint) {
        this.player = player;
        this.stage = (totalLevel / ConfigFile.stage_level) % 10;
        this.realm = totalLevel / ConfigFile.stage_level / 10;
        this.level = totalLevel - stage * ConfigFile.stage_level + realm * 10 * ConfigFile.stage_level;
        this.exp = currentExp;
        this.stagePoint = stagePoint;
        this.realmPoint = realmPoint;
        this.updateExpBar();
    }

    public void addRealm() {
        setRealm(realm + 1);
    }

    public void addStage() {
        setStage(stage + 1);
    }

    public void addLevel(int upgrade) {
        JustPlayerUpgradeEvent upgradeEvent = new JustPlayerUpgradeEvent(this, upgrade);
        upgradeEvent.call();
        if (upgradeEvent.isCancelled()) return;
        upgrade = upgradeEvent.getUpgrade();

        int oldLevel = level;
        setLevel(level + upgrade);

        IEvent upgradedEvent = new JustPlayerUpgradedEvent(this, upgrade, oldLevel, level);
        upgradedEvent.call();
    }

    public void setRealm(int realm) {
        this.realm = Math.min(ConfigFile.realm_layer, realm);
    }

    public void setStage(int stage) {
        this.stage = Math.min(10, stage);
    }

    public void setLevel(int level) {
        this.level = Math.min(ConfigFile.stage_level, level);
    }

    public void setExp(double exp) {
        double upgradeRequire = LevelUtil.getUpgradeRequireExp(level);
        double value = Math.min(upgradeRequire, exp);
        if (value == upgradeRequire) {
            level++;
            this.exp = 0;
            return;
        }
        this.exp = value;
    }

    public void updateExpBar() {
        this.player.setLevel(level);
        this.player.setExp((float) (exp / LevelUtil.getUpgradeRequireExp(level)));
    }

    public void save() {
        int totalLevel = getTotalLevel();
        JustLevel.getInstance().getStorageManager().updatePlayerData(player, totalLevel, exp, stagePoint, realmPoint);
    }

    public int getTotalLevel() {
        return level + stage * ConfigFile.stage_level + realm * 10 * ConfigFile.stage_level;
    }

    public void addExp(double value) {
        if (realm == ConfigFile.realm_layer) return;
        if (level == ConfigFile.stage_level) return;

        JustPlayerExpChangeEvent changeEvent = new JustPlayerExpChangeEvent(this, value);
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
        IEvent expChangedEvent = new JustPlayerExpChangedEvent(this, value);
        expChangedEvent.call();

        updateExpBar();
    }

    public void addStagePoint(int i) {
        this.stagePoint += i;
    }

    public void addRealmPoint(int i) {
        this.realmPoint += i;
    }
}
