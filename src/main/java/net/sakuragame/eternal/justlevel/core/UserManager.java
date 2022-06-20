package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.gemseconomy.api.GemsEconomyAPI;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.PlayerBrokenEvent;
import net.sakuragame.eternal.justlevel.core.level.Realm;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.Bukkit;
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

    public void saveAccount(UUID uuid) {
        PlayerLevelData account = accounts.remove(uuid);
        if (account == null) return;
        account.saveData();
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

    public void tryBreakStage(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerLevelData account = this.getAccount(player.getUniqueId());

        if (account == null) return;
        if (!account.canBreakStage()) return;

        Realm realm = ConfigFile.realmSetting.get(account.getRealm());

        if (account.getStagePoints() < realm.getStageConsume()) return;
        if (GemsEconomyAPI.getBalance(uuid) < realm.getStageBreakPrice()) return;

        account.setLevel(0);
        account.setExp(0);
        account.addStage();
        account.takeStagePoints(realm.getStageConsume());
        GemsEconomyAPI.withdraw(uuid, realm.getStageBreakPrice());

        PlayerBrokenEvent.Stage event = new PlayerBrokenEvent.Stage(player, account.getStage());
        event.call();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, account::saveData);
    }

    public void tryBreakRealm(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerLevelData account = this.getAccount(player.getUniqueId());

        if (account == null) return;
        if (!account.canBreakRealm()) return;

        Realm realm = ConfigFile.realmSetting.get(account.getRealm());

        if (account.getRealmPoints() < realm.getRealmConsume()) return;
        if (GemsEconomyAPI.getBalance(uuid) < realm.getRealmBreakPrice()) return;

        account.setLevel(0);
        account.setExp(0);
        account.setStage(1);
        account.addRealm();
        account.takeRealmPoints(realm.getRealmConsume());
        GemsEconomyAPI.withdraw(uuid, realm.getRealmBreakPrice());

        PlayerBrokenEvent.Realm event = new PlayerBrokenEvent.Realm(player, account.getRealm());
        event.call();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, account::saveData);
    }
}
