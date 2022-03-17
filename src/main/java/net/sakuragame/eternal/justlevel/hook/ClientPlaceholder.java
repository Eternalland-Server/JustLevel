package net.sakuragame.eternal.justlevel.hook;

import net.sakuragame.eternal.dragoncore.network.PacketSender;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.core.level.Define;
import net.sakuragame.eternal.justlevel.core.level.Realm;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ClientPlaceholder {

    private final static String PLAYER_CURRENT_EXP_PAPI = "player_current_exp";
    private final static String PLAYER_UPGRADE_EXP_PAPI = "player_upgrade_exp";
    private final static String PLAYER_STAGE_PAPI = "player_stage";
    private final static String PLAYER_REALM_PAPI = "player_realm";
    private final static String PLAYER_STAGE_POINTS_PAPI = "player_stage_points";
    private final static String PLAYER_REALM_POINTS_PAPI = "player_realm_points";
    private final static String STAGE_CAN_BREAK_PAPI = "stage_can_break";
    private final static String REALM_CAN_BREAK_PAPI = "realm_can_break";
    private final static String STAGE_BREAK_REQUIRE_1_PAPI = "stage_break_require_1";
    private final static String STAGE_BREAK_REQUIRE_2_PAPI = "stage_break_require_2";
    private final static String STAGE_BREAK_REQUIRE_3_PAPI = "stage_break_require_3";
    private final static String REALM_BREAK_REQUIRE_1_PAPI = "realm_break_require_1";
    private final static String REALM_BREAK_REQUIRE_2_PAPI = "realm_break_require_2";
    private final static String REALM_BREAK_REQUIRE_3_PAPI = "realm_break_require_3";

    private final static DecimalFormat FORMAT = new DecimalFormat("0");

    public static void send(Player player) {
        sendExp(player);
        sendLevel(player);
        sendPoints(player);
    }

    public static void sendExp(Player player) {
        PlayerLevelData account = JustLevelAPI.getUserData(player);
        if (account == null) return;

        Map<String, String> map = new HashMap<String, String>() {{
            put(PLAYER_CURRENT_EXP_PAPI, FORMAT.format(account.getExp()));
            put(PLAYER_UPGRADE_EXP_PAPI, FORMAT.format(account.getUpgradeExp()));
        }};

        PacketSender.sendSyncPlaceholder(player, map);
    }

    public static void sendLevel(Player player) {
        PlayerLevelData account = JustLevelAPI.getUserData(player);
        if (account == null) return;

        Map<String, String> map = new HashMap<String, String>() {{
            put(PLAYER_STAGE_PAPI, String.valueOf(account.getStage()));
            put(PLAYER_REALM_PAPI, JustLevel.getUserManager().getRealmName(player.getUniqueId()));
        }};

        PacketSender.sendSyncPlaceholder(player, map);
    }

    public static void sendPoints(Player player) {
        PlayerLevelData account = JustLevelAPI.getUserData(player);
        if (account == null) return;

        Map<String, String> map = new HashMap<String, String>() {{
            put(PLAYER_STAGE_POINTS_PAPI, String.valueOf(account.getStagePoints()));
            put(PLAYER_REALM_POINTS_PAPI, String.valueOf(account.getRealmPoints()));
        }};

        PacketSender.sendSyncPlaceholder(player, map);
    }

    public static void sendBreakRequire(Player player) {
        PlayerLevelData account = JustLevelAPI.getUserData(player);
        if (account == null) return;

        Realm realm = ConfigFile.realmSetting.get(account.getRealm());
        double balance = JustLevel.getInstance().getEconomy().getBalance(player);

        String s_rq_1 = (account.isMaxLevel() ? "⁍" : "⁌") + " &f等级: " + (account.isMaxLevel() ? "&a" : "&c") + Define.Level.getMax();
        String s_rq_2 = (account.getStagePoints() >= realm.getStageConsume() ? "⁍" : "⁌") + " &f突破点: " + (account.getStagePoints() >= realm.getStageConsume() ? "&a" : "&c") + realm.getStageConsume();
        String s_rq_3 = (balance >= realm.getStageBreakPrice() ? "⁍" : "⁌") + " &f金币: " + (balance >= realm.getStageBreakPrice() ? "&a" : "&c") + realm.getStageBreakPrice();
        String r_rq_1 = (account.isMaxStage() && account.isMaxLevel() ? "⁍" : "⁌") + " &f阶段: " + (account.isMaxStage() ? "&a" : "&c") + Define.Stage.getMax() + "(并满级)";
        String r_rq_2 = (account.getRealmPoints() >= realm.getRealmConsume() ? "⁍" : "⁌") + " &f境界点: " + (account.getRealmPoints() >= realm.getRealmConsume() ? "&a" : "&c") + realm.getRealmConsume();
        String r_rq_3 = (balance >= realm.getRealmBreakPrice() ? "⁍" : "⁌") + " &f金币: " + (balance >= realm.getRealmBreakPrice() ? "&a" : "&c") + realm.getRealmBreakPrice();

        Map<String, String> map = new HashMap<String, String>() {{
            put(STAGE_CAN_BREAK_PAPI, account.canBreakStage() && balance >= realm.getStageBreakPrice() ? "1" : "0");
            put(REALM_CAN_BREAK_PAPI, account.canBreakRealm() && balance >= realm.getRealmBreakPrice() ? "1" : "0");
            put(STAGE_BREAK_REQUIRE_1_PAPI, s_rq_1);
            put(STAGE_BREAK_REQUIRE_2_PAPI, s_rq_2);
            put(STAGE_BREAK_REQUIRE_3_PAPI, s_rq_3);
            put(REALM_BREAK_REQUIRE_1_PAPI, r_rq_1);
            put(REALM_BREAK_REQUIRE_2_PAPI, r_rq_2);
            put(REALM_BREAK_REQUIRE_3_PAPI, r_rq_3);
        }};

        PacketSender.sendSyncPlaceholder(player, map);
    }
}
