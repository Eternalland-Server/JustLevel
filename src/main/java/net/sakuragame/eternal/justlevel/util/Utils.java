package net.sakuragame.eternal.justlevel.util;

import net.sakuragame.eternal.justlevel.core.level.Define;
import net.sakuragame.eternal.justlevel.file.sub.ConfigFile;
import org.bukkit.entity.Player;

import java.util.Calendar;

public class Utils {

    public static double getUpgradeRequireExp(int realm, int stage, int level) {
        int max = Define.Level.getMax();
        int total = (realm - 1) * max * 10 + (stage - 1) * max + level;
        double difficulty = total / (13820d / realm) + realm + stage;
        int result = (int) (total / Math.pow(difficulty / 6, -1));

        return result + 20;
    }

    public static double getExpAddition(Player player) {
        for (String s : ConfigFile.expAddition.keySet()) {
            if (!player.hasPermission(s)) continue;
            return ConfigFile.expAddition.get(s);
        }

        return 0;
    }

    public static long getNextDayTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    public static void main(String[] args) {
        long v0 = 0;

        for (int realm = 1; realm <= 21; realm++) {

            long v1 = 0;
            for (int stage = 1; stage <= 10; stage++) {

                long v2 = 0;
                for (int level = 1; level <= 100; level++) {
                    double exp = Utils.getUpgradeRequireExp(realm, stage, level);
                    v1 += exp;
                    v2 += exp;
                }

                System.out.println(realm + " | " + stage + " : " + v2);
            }

            System.out.printf("%s # %s%n", realm, v1);
            v0 += v1;
        }

        System.out.println("total: " + v0);
    }
}
