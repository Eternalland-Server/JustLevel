package net.sakuragame.eternal.justlevel.core.user;

import lombok.Getter;
import net.sakuragame.eternal.justlevel.core.level.Define;
import net.sakuragame.eternal.justlevel.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Getter
public abstract class LevelAccount {

    private final UUID uuid;
    private int realm;
    private int stage;

    private int level;
    private double exp;

    private int stagePoints;
    private int realmPoints;

    public LevelAccount(UUID uuid) {
        this.uuid = uuid;
        this.realm = 1;
        this.stage = 1;
        this.level = 0;
        this.exp = 0;
        this.stagePoints = 0;
        this.realmPoints = 0;
    }

    public LevelAccount(UUID uuid, int realm, int stage, int level, double exp, int stagePoints, int realmPoints) {
        this.uuid = uuid;
        this.realm = realm;
        this.stage = stage;
        this.level = level;
        this.exp = exp;
        this.stagePoints = stagePoints;
        this.realmPoints = realmPoints;
    }

    public abstract void saveData();

    public abstract void addLevel(int value);

    public abstract void addExp(double value);

    public UUID getUUID() {
        return uuid;
    }

    public void addRealm() {
        this.setStage(this.realm + 1);
    }

    public void addStage() {
        this.setStage(this.stage + 1);
    }

    public void addRealmPoints(int i) {
        this.setRealmPoints(this.realmPoints + i);
    }

    public void addStagePoints(int i) {
        this.setStagePoints(this.stagePoints + i);
    }

    public void takeRealmPoints(int i) {
        this.setRealmPoints(this.realmPoints - i);
    }

    public void takeStagePoints(int i) {
        this.setStagePoints(this.stagePoints - i);
    }

    public void setRealm(int i) {
        this.realm = Math.max(1, Math.min(Define.Realm.getMax(), i));
    }

    public void setStage(int i) {
        this.stage = Math.max(1, Math.min(Define.Stage.getMax(), i));
    }

    public void setLevel(int i) {
        this.level = Math.max(0, Math.min(Define.Level.getMax(), i));
        this.updateVanillaInfo();
    }

    public void setExp(double exp) {
        this.exp = Math.max(0, Math.min(Utils.getLevelUpExp(this.level), exp));
    }

    public void setRealmPoints(int realmPoints) {
        this.realmPoints = Math.max(0, realmPoints);
    }

    public void setStagePoints(int stagePoints) {
        this.stagePoints = Math.max(0, stagePoints);
    }

    public boolean isMaxRealm() {
        return this.realm == Define.Realm.getMax();
    }

    public boolean isMaxStage() {
        return this.stage == Define.Stage.getMax();
    }

    public boolean isMaxLevel() {
        return this.level == Define.Level.getMax();
    }

    public boolean canBreakRealm() {
        return !this.isMaxRealm() && this.isMaxStage() && this.isMaxLevel();
    }

    public boolean canBreakStage() {
        return this.isMaxLevel();
    }

    public double getUpgradeExp() {
        return Utils.getLevelUpExp(this.level);
    }

    public void updateVanillaInfo() {
        this.updateVanillaExp();
        this.updateVanillaLevel();
    }

    public void updateVanillaExp() {
        double upgrade = getUpgradeExp();
        getBukkitPlayer().setExp((float) (Math.min(upgrade, this.exp) / upgrade));
    }

    public void updateVanillaLevel() {
        getBukkitPlayer().setLevel(this.level);
    }

    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }
}
