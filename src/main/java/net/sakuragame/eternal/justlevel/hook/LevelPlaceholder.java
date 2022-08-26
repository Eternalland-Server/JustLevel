package net.sakuragame.eternal.justlevel.hook;

import com.taylorswiftcn.justwei.util.UnitConvert;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.core.level.Define;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;
import java.util.UUID;

public class LevelPlaceholder extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "justlevel";
    }

    @Override
    public @NotNull String getAuthor() {
        return "justwei";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(@Nullable OfflinePlayer player, @NotNull String param) {
        if (player == null) return "";

        UUID uuid = player.getUniqueId();

        if (param.equalsIgnoreCase("prefix")) {
            return JustLevelAPI.getRealmPrefix(uuid);
        }

        if (param.equalsIgnoreCase("level")) {
            return String.valueOf(JustLevelAPI.getLevel(uuid));
        }

        if (param.equalsIgnoreCase("exp")) {
            return String.valueOf(JustLevelAPI.getRealmPrefix(uuid));
        }

        if (param.equalsIgnoreCase("upgrade_exp")) {
            return String.valueOf(JustLevelAPI.getUpgradeExp(uuid));
        }

        if (param.equalsIgnoreCase("stage")) {
            return String.valueOf(JustLevelAPI.getStage(uuid));
        }

        if (param.equalsIgnoreCase("realm")) {
            return String.valueOf(JustLevelAPI.getRealm(uuid));
        }

        if (param.equalsIgnoreCase("realm_name")) {
            return String.valueOf(JustLevelAPI.getRealmName(uuid));
        }

        if (param.equalsIgnoreCase("realm_points")) {
            return String.valueOf(JustLevelAPI.getRealmPoints(uuid));
        }

        if (param.equalsIgnoreCase("stage_points")) {
            return String.valueOf(JustLevelAPI.getStagePoints(uuid));
        }

        if (param.equalsIgnoreCase("daily_data")) {
            PlayerLevelData account = JustLevelAPI.getUserData(uuid);
            int current = (int) account.getDailyExpLimit();
            String left = current >= 100000 ? UnitConvert.formatEN(UnitConvert.TenThousand, current) : current + "";
            String right = UnitConvert.formatCN(UnitConvert.TenThousand, Define.Daily.getMax());

            return "§f" + left + "§2/§e" + right;
        }

        if (param.equalsIgnoreCase("daily_chart")) {
            PlayerLevelData account = JustLevelAPI.getUserData(uuid);
            double current = account.getDailyExpLimit();
            double rate = current / Define.Daily.getMax();

            String s = "§3&l[%s§3§l]";

            StringBuilder sb = new StringBuilder();
            sb.append("§e");

            int index = (int) (rate * 64);
            for (int i = 0; i < 64; i++) {
                if (i == (int) (rate * 64)) {
                    sb.append("§8");
                }

                sb.append("|");

                if (i == 32) {
                    sb
                            .append("§b")
                            .append(new DecimalFormat("#0.0").format(rate * 100))
                            .append("%");

                    sb.append(i > index ? "§8" : "§e");
                }
            }

            return "§3&l[" + sb + "§3§l]";
        }

        return "";
    }
}
