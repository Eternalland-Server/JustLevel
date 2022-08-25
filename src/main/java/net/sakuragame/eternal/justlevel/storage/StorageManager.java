package net.sakuragame.eternal.justlevel.storage;

import net.sakuragame.eternal.dragoncore.util.Pair;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.util.Utils;
import net.sakuragame.serversystems.manage.api.database.DataManager;
import net.sakuragame.serversystems.manage.api.database.DatabaseQuery;
import net.sakuragame.serversystems.manage.api.redis.RedisManager;
import net.sakuragame.serversystems.manage.client.api.ClientManagerAPI;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StorageManager {

    private final DataManager dataManager;
    private final RedisManager redisManager;

    public StorageManager() {
        this.dataManager = ClientManagerAPI.getDataManager();
        this.redisManager = ClientManagerAPI.getRedisManager();
    }

    public void init() {
        AccountTable.JUST_LEVEL_ACCOUNT.createTable();
    }

    public PlayerLevelData loadData(UUID uuid) {
        int uid = ClientManagerAPI.getUserID(uuid);
        if (uid == -1) return null;

        try (DatabaseQuery query = this.dataManager.sqlQuery(
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

    public Map<Integer, Integer> getAllPlayerLevel() {
        Map<Integer, Integer> map = new HashMap<>();

        try (DatabaseQuery query = this.dataManager.createQuery("SELECT * FROM " + AccountTable.JUST_LEVEL_ACCOUNT.getTableName())) {
            ResultSet result = query.getResultSet();
            while (result.next()) {
                int uid = result.getInt("uid");
                int realm = result.getInt("realm");
                int stage = result.getInt("stage");
                int level = result.getInt("level");

                int total = (realm - 1) * 2000 + (stage - 1) * 200 + level;

                map.put(uid, total);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public void saveData(UUID uuid, int realm, int stage, int level, double exp, int realmPoint, int stagePoint) {
        int uid = ClientManagerAPI.getUserID(uuid);
        if (uid == -1) return;

        this.dataManager.executeReplace(
                AccountTable.JUST_LEVEL_ACCOUNT.getTableName(),
                new String[]{"uid", "realm", "stage", "level", "exp", "realm_point", "stage_point"},
                new Object[]{uid, realm, stage, level, exp, realmPoint, stagePoint}
        );
    }

    public void setUseCard(UUID uuid, String card, int expire) {
        String key = this.getCardUserKey(uuid);
        this.redisManager.getStandaloneConn().async().set(key, card);
        this.redisManager.getStandaloneConn().async().expire(key, expire);
    }

    public Pair<String, Long> getUseCard(UUID uuid) {
        String key = this.getCardUserKey(uuid);
        String card = this.redisManager.getStandaloneConn().sync().get(key);
        if (card == null) return null;
        long expire = this.redisManager.getStandaloneConn().sync().ttl(key);

        return new Pair<>(card, expire);
    }

    public void setDailyExp(UUID uuid, double v) {
        String key = this.getDailyUserKey(uuid);
        this.redisManager.getStandaloneConn().async().set(key, v + "");
        this.redisManager.getStandaloneConn().async().expire(key, Utils.getNextDayTime());
    }

    public double getDailyExp(UUID uuid) {
        String key = this.getDailyUserKey(uuid);
        String s = this.redisManager.getStandaloneConn().sync().get(key);
        if (s == null) return 0d;

        return Double.parseDouble(s);
    }

    public String getCardUserKey(UUID uuid) {
        return "JustLevel:Card:" + uuid.toString();
    }

    public String getDailyUserKey(UUID uuid) {
        return "JustLevel:Daily:" + uuid.toString();
    }
}
