package net.sakuragame.eternal.justlevel.api;

import net.sakuragame.eternal.justlevel.JustLevel;
import org.bukkit.Location;

public class PropGenerateAPI {

    public static void spawnExpProp(Location location, int value, int amount) {
        JustLevel.getPropGenerate().spawnExpProp(location, value, amount);
    }

    public static void spawnLevelProp(Location location, int value, int amount) {
        JustLevel.getPropGenerate().spawnLevelProp(location, value, amount);
    }

    public static void spawnMoneyProp(Location location, int value, int amount) {
        JustLevel.getPropGenerate().spawnMoneyProp(location, value, amount);
    }

    public static void spawnCoinsProp(Location location, int value, int amount) {
        JustLevel.getPropGenerate().spawnCoinsProp(location, value, amount);
    }

    public static void spawn(int type, Location location, int value, int amount) {
        JustLevel.getPropGenerate().generate(type, location, value, amount);
    }

}
