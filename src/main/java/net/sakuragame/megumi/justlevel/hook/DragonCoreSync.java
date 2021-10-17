package net.sakuragame.megumi.justlevel.hook;

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
        REALM_POINTS("justlevel_realm_points");

        private String name;

        PlaceHolder(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final DecimalFormat format = new DecimalFormat("#.#");

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

    public static void send(Player player, double currentExp, double upgradeExp) {
        Map<String, String> map = new HashMap<>();
        map.put(PlaceHolder.CURRENT_EXP.getName(), format.format(currentExp));
        map.put(PlaceHolder.UPGRADE_EXP.getName(), format.format(upgradeExp));

        sendPacket(player, map);
    }

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

    private static void sendPacket(Player player, Map<String, String> map) {
        PacketSender.sendSyncPlaceholder(player, map);
    }
}
