package com.taylorswiftcn.megumi.justlevel.storage;

import com.taylorswiftcn.megumi.justlevel.JustLevel;
import com.taylorswiftcn.megumi.justlevel.level.PlayerLevelData;
import net.sakuragame.serversystems.manage.api.database.DataManager;
import net.sakuragame.serversystems.manage.api.database.DatabaseQuery;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {

    private JustLevel plugin;
    private DataManager dataManager;

    public StorageManager(JustLevel plugin) {
        this.plugin = plugin;
        this.dataManager = ClientManagerAPI.getDataManager();
    }

    public void init() {
        AccountTable.JUST_LEVEL_ACCOUNT.createTable();
    }

    public void insertPlayerData(Player player) {
        dataManager.insert(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[]{"uuid", "player"},
                new String[]{player.getUniqueId().toString(), player.getName()}
        );
    }

    public PlayerLevelData getPlayerData(Player player) {
        DatabaseQuery query = dataManager.sqlQuery(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                "uuid",
                player.getUniqueId().toString()
        );

        try {
            query.execute();
            ResultSet result = query.getResultSet();
            if (result.next()) {
                int total_level = result.getInt("level");
                double current_exp = result.getDouble("exp");

                return new PlayerLevelData(player, total_level, current_exp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            query.close();
        }

        insertPlayerData(player);

        return new PlayerLevelData(player, 0, 0);
    }

    public void updatePlayerData(Player player, int level, double exp) {
        dataManager.update(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[]{"level", "exp"},
                new String[]{String.valueOf(level), String.valueOf(exp)},
                new String[]{"uuid"},
                new String[]{player.getUniqueId().toString()}
        );
    }

    public void updateAllPlayerData() {
        List<Object[]> datum = new ArrayList<>();
        for (PlayerLevelData data : plugin.getPlayerData().values()) {
            datum.add(new Object[] {data.getPlayer().getUniqueId().toString(), data.getTotalLevel(), data.getExp()});
        }

        dataManager.replace(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[] {"uuid", "level", "exp"},
                datum
        );
    }
}
