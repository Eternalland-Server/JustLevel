package net.sakuragame.eternal.justlevel.storage;

import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.serversystems.manage.api.database.DataManager;
import net.sakuragame.serversystems.manage.api.database.DatabaseQuery;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;

import java.sql.ResultSet;
import java.util.UUID;

public class StorageManager {

    private final DataManager dataManager;

    public StorageManager() {
        this.dataManager = ClientManagerAPI.getDataManager();
    }

    public void init() {
        AccountTable.JUST_LEVEL_ACCOUNT.createTable();
    }

    public PlayerLevelData loadData(UUID uuid) {
        int uid = ClientManagerAPI.getUserID(uuid);
        if (uid == -1) return null;

        try (DatabaseQuery query = dataManager.sqlQuery(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                "uid",
                uid
        )) {
            ResultSet result = query.getResultSet();
            if (result.next()) {
                int realm = result.getInt("realm");
                int stage = result.getInt("stage");
                int level = result.getInt("level");
                double exp = result.getDouble("exp");
                int stagePoint = result.getInt("stage_point");
                int realmPoint = result.getInt("realm_point");

                return new PlayerLevelData(uuid, realm, stage, level, exp, stagePoint, realmPoint);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new PlayerLevelData(uuid);
    }

    public void saveData(UUID uuid, int realm, int stage, int level, double exp, int realmPoint, int stagePoint) {
        int uid = ClientManagerAPI.getUserID(uuid);
        if (uid == -1) return;

        dataManager.executeReplace(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[]{"uid", "realm", "stage", "level", "exp", "realm_point", "stage_point"},
                new Object[]{uid, realm, stage, level, exp, realmPoint, stagePoint}
        );
    }
}
