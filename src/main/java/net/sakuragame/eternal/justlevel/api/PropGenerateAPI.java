package net.sakuragame.eternal.justlevel.api;

import net.sakuragame.eternal.justlevel.JustLevel;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

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
        JustLevel.getPropGenerate().spawn(type, location, value, amount);
    }

    public static ItemStack generate(int type, int value, int amount) {
        return JustLevel.getPropGenerate().generate(type, value, amount);
    }

}
