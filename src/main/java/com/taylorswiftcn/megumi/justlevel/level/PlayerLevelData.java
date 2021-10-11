package com.taylorswiftcn.megumi.justlevel.level;

import com.taylorswiftcn.megumi.justlevel.JustLevel;
import com.taylorswiftcn.megumi.justlevel.event.JustPlayerExpChangeEvent;
import com.taylorswiftcn.megumi.justlevel.event.JustPlayerUpgradeEvent;
import com.taylorswiftcn.megumi.justlevel.file.sub.ConfigFile;
import com.taylorswiftcn.megumi.justlevel.util.LevelUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@Getter
@Setter
public class PlayerLevelData {

    private Player player;
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

    public PlayerLevelData(Player player, int total_level, double current_exp) {
        this.player = player;
        this.stage = (total_level / ConfigFile.stage_level) % 10;
        this.realm = total_level / ConfigFile.stage_level / 10;
        this.level = total_level - stage * ConfigFile.stage_level + realm * 10 * ConfigFile.stage_level;
        this.exp = current_exp;
        this.updateExpBar();
    }

    public void updateExpBar() {
        this.player.setLevel(level);
        this.player.setExp((float) (exp / LevelUtil.getUpgradeRequireExp(level)));
    }

    public void save() {
        int total_level = getTotalLevel();
        JustLevel.getInstance().getStorageManager().updatePlayerData(player, total_level, exp);
    }

    public int getTotalLevel() {
        return level + stage * ConfigFile.stage_level + realm * 10 * ConfigFile.stage_level;
    }

    public void addExp(double value) {
        if (realm >= ConfigFile.realm_layer) return;
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
            // send msg!
            int origin = level;
            setLevel(level + upgrade);
            JustPlayerUpgradeEvent upgradeEvent = new JustPlayerUpgradeEvent(player, origin, this);
            Bukkit.getPluginManager().callEvent(upgradeEvent);
        }
        JustPlayerExpChangeEvent expChangeEvent = new JustPlayerExpChangeEvent(player, value, this);
        Bukkit.getPluginManager().callEvent(expChangeEvent);
    }
}
