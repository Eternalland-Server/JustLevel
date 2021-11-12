package net.sakuragame.eternal.justlevel.storage;

import net.sakuragame.eternal.justlevel.level.PlayerLevelData;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.serversystems.manage.api.database.DataManager;
import net.sakuragame.serversystems.manage.api.database.DatabaseQuery;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StorageManager {

    private final JustLevel plugin;
    private final DataManager dataManager;

    public StorageManager(JustLevel plugin) {
        this.plugin = plugin;
        this.dataManager = ClientManagerAPI.getDataManager();
    }

    public void init() {
        AccountTable.JUST_LEVEL_ACCOUNT.createTable();
    }

    public void insertPlayerData(Player player) {
        dataManager.executeInsert(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[]{"uuid", "player"},
                new String[]{player.getUniqueId().toString(), player.getName()}
        );
    }

    public PlayerLevelData getPlayerData(Player player) {

        try (DatabaseQuery query = dataManager.sqlQuery(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                "uuid",
                player.getUniqueId().toString()
        )) {
            ResultSet result = query.getResultSet();
            if (result.next()) {
                int totalLevel = result.getInt("level");
                double currentExp = result.getDouble("exp");
                int stagePoint = result.getInt("stage_point");
                int realmPoint = result.getInt("realm_point");

                return new PlayerLevelData(player, totalLevel, currentExp, stagePoint, realmPoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        insertPlayerData(player);

        return new PlayerLevelData(player);
    }

    public void updatePlayerData(Player player, int level, double exp, int stagePoint, int realmPoint) {
        dataManager.executeUpdate(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[]{"level", "exp", "stage_point", "realm_point"},
                new String[]{String.valueOf(level), String.valueOf(exp), String.valueOf(stagePoint), String.valueOf(realmPoint)},
                new String[]{"uuid"},
                new String[]{player.getUniqueId().toString()}
        );
    }

    public void updateAllPlayerData() {
        List<Object[]> datum = new ArrayList<>();
        for (PlayerLevelData data : plugin.getPlayerData().values()) {
            datum.add(new Object[] {data.getTotalLevel(), data.getExp(), data.getStagePoints(), data.getRealmPoints(), data.getPlayer().getUniqueId().toString()});
        }

        dataManager.executeSQLBatch("update " + AccountTable.JUST_LEVEL_ACCOUNT.getTableName() + " SET level = ?, exp = ?, stage_point = ?, realm_point = ? WHERE uuid = ?", datum);
    }
}
