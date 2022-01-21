package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.PlayerBrokenEvent;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserManager {

    private final JustLevel plugin;
    private final Map<UUID, PlayerLevelData> accounts;

    public UserManager(JustLevel plugin) {
        this.plugin = plugin;
        this.accounts = new HashMap<>();
    }

    public PlayerLevelData getAccount(UUID uuid) {
        return accounts.get(uuid);
    }

    public void loadAccount(UUID uuid) {
        PlayerLevelData data = JustLevel.getStorageManager().loadData(uuid);
        accounts.put(uuid, data);
    }

    public void removeAccount(UUID uuid) {
        accounts.remove(uuid);
    }

    public String getRealmPrefix(UUID uuid) {
        PlayerLevelData account = this.getAccount(uuid);
        if (account == null) return "";

        return ConfigFile.realmSetting.get(account.getRealm()).getPrefix();
    }

    public String getRealmName(UUID uuid) {
        PlayerLevelData account = this.getAccount(uuid);
        if (account == null) return "";

        return ConfigFile.realmSetting.get(account.getRealm()).getName();
    }

    public boolean tryBreakStage(Player player) {
        PlayerLevelData account = this.getAccount(player.getUniqueId());

        if (account == null) return false;

        if (!account.canBreakStage()) {
            return false;
        }

        Realm realm = ConfigFile.realmSetting.get(account.getRealm());

        account.setLevel(0);
        account.setExp(0);
        account.addStage();
        account.takeStagePoints(realm.getStageConsume());
        plugin.getEconomy().withdrawPlayer(player, realm.getStageBreakPrice());

        PlayerBrokenEvent.Stage event = new PlayerBrokenEvent.Stage(player, account.getStage());
        event.call();

        return true;
    }

    public boolean tryBreakRealm(Player player) {
        PlayerLevelData account = this.getAccount(player.getUniqueId());

        if (account == null) return false;

        if (!account.canBreakStage()) {
            return true;
        }

        Realm realm = ConfigFile.realmSetting.get(account.getRealm());

        account.setLevel(0);
        account.setExp(0);
        account.setStage(1);
        account.addRealm();
        account.takeRealmPoints(realm.getRealmConsume());
        plugin.getEconomy().withdrawPlayer(player, realm.getRealmBreakPrice());

        PlayerBrokenEvent.Realm event = new PlayerBrokenEvent.Realm(player, account.getRealm());
        event.call();

        return false;
    }
}
