package net.sakuragame.eternal.justlevel.hook;

import eos.moe.dragoncore.network.PacketSender;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class DragonCoreSync {

    public enum PlaceHolder {

        CURRENT_EXP("justlevel_current_exp"),
        UPGRADE_EXP("justlevel_upgrade_exp"),
        CURRENT_STAGE("justlevel_current_stage"),
        CURRENT_REALM("justlevel_current_realm"),
        STAGE_POINTS("justlevel_stage_points"),
        REALM_POINTS("justlevel_realm_points"),
        STAGE_BREAK_MEET_REQUIRE("justlevel_stage_break_meet_require"),
        REALM_BREAK_MEET_REQUIRE("justlevel_realm_break_meet_require"),
        STAGE_BREAK_LEVEL_REQUIRE("justlevel_stage_break_level_require"),
        STAGE_BREAK_POINTS_REQUIRE("justlevel_stage_break_points_require"),
        STAGE_BREAK_PRICE_REQUIRE("justlevel_stage_break_price_require"),
        REALM_BREAK_STAGE_REQUIRE("justlevel_realm_break_stage_require"),
        REALM_BREAK_POINTS_REQUIRE("justlevel_realm_break_points_require"),
        REALM_BREAK_PRICE_REQUIRE("justlevel_realm_break_price_require");

        private String name;

        PlaceHolder(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final DecimalFormat format = new DecimalFormat("#.#");

    public static void send(Player player, PlaceHolder placeHolder, int value) {
        Map<String, String> map = new HashMap<>();
        map.put(placeHolder.getName(), String.valueOf(value));

        sendPacket(player, map);
    }

    public static void send(Player player, PlaceHolder placeHolder, double value) {
        Map<String, String> map = new HashMap<>();
        map.put(placeHolder.getName(), String.valueOf(value));

        sendPacket(player, map);
    }

    public static void send(Player player, PlaceHolder placeHolder, String value) {
        Map<String, String> map = new HashMap<>();
        map.put(placeHolder.getName(), value);

        sendPacket(player, map);
    }

    public static void send(Player player, double currentExp, double upgradeExp, int stage, String realm, int stagePoints, int realmPoints) {
        Map<String, String> map = new HashMap<>();
        map.put(PlaceHolder.CURRENT_EXP.getName(), format.format(currentExp));
        map.put(PlaceHolder.UPGRADE_EXP.getName(), format.format(upgradeExp));
        map.put(PlaceHolder.CURRENT_STAGE.getName(), String.valueOf(stage));
        map.put(PlaceHolder.CURRENT_REALM.getName(), String.valueOf(realm));
        map.put(PlaceHolder.STAGE_POINTS.getName(), String.valueOf(stagePoints));
        map.put(PlaceHolder.REALM_POINTS.getName(), String.valueOf(realmPoints));

        sendPacket(player, map);
    }

    public static void sendExp(Player player, double currentExp, double upgradeExp) {
        Map<String, String> map = new HashMap<>();
        map.put(PlaceHolder.CURRENT_EXP.getName(), format.format(currentExp));
        map.put(PlaceHolder.UPGRADE_EXP.getName(), format.format(upgradeExp));

        sendPacket(player, map);
    }

    public static void sendRequire(Player player, int meetStage, int meetRealm,
                                   String stageLevelRequire, String stagePointsRequire, String stagePriceRequire,
                                   String realmStageRequire, String realmPointsRequire, String realmPriceRequire) {
        Map<String, String> map = new HashMap<>();
        map.put(PlaceHolder.STAGE_BREAK_MEET_REQUIRE.getName(), String.valueOf(meetStage));
        map.put(PlaceHolder.REALM_BREAK_MEET_REQUIRE.getName(), String.valueOf(meetRealm));
        map.put(PlaceHolder.STAGE_BREAK_LEVEL_REQUIRE.getName(), stageLevelRequire);
        map.put(PlaceHolder.STAGE_BREAK_POINTS_REQUIRE.getName(), stagePointsRequire);
        map.put(PlaceHolder.STAGE_BREAK_PRICE_REQUIRE.getName(), stagePriceRequire);
        map.put(PlaceHolder.REALM_BREAK_STAGE_REQUIRE.getName(), realmStageRequire);
        map.put(PlaceHolder.REALM_BREAK_POINTS_REQUIRE.getName(), realmPointsRequire);
        map.put(PlaceHolder.REALM_BREAK_PRICE_REQUIRE.getName(), realmPriceRequire);

        sendPacket(player, map);
    }

    private static void sendPacket(Player player, Map<String, String> map) {
        PacketSender.sendSyncPlaceholder(player, map);
    }
}
