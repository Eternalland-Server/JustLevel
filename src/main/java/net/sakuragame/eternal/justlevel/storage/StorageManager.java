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
        int uid = ClientManagerAPI.getUserID(player.getUniqueId());

        dataManager.executeInsert(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[]{"uid"},
                new Object[]{uid}
        );
    }

    public PlayerLevelData getPlayerData(Player player) {
        int uid = ClientManagerAPI.getUserID(player.getUniqueId());

        try (DatabaseQuery query = dataManager.sqlQuery(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                "uid",
                uid
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
        int uid = ClientManagerAPI.getUserID(player.getUniqueId());

        dataManager.executeUpdate(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[]{"level", "exp", "stage_point", "realm_point"},
                new Object[]{level, exp, stagePoint, realmPoint},
                new String[]{"uid"},
                new Object[]{uid}
        );
    }

    public void updateAllPlayerData() {
        List<Object[]> datum = new ArrayList<>();
        for (PlayerLevelData data : plugin.getPlayerData().values()) {
            int uid = ClientManagerAPI.getUserID(data.getPlayer().getUniqueId());
            datum.add(new Object[] {data.getTotalLevel(), data.getExp(), data.getStagePoints(), data.getRealmPoints(), uid});
        }

        dataManager.executeSQLBatch("update " + AccountTable.JUST_LEVEL_ACCOUNT.getTableName() + " SET level = ?, exp = ?, stage_point = ?, realm_point = ? WHERE uid = ?", datum);
    }
}
