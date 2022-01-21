package net.sakuragame.eternal.justlevel.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.JustLevel;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public @Nullable String onRequest(@Nullable OfflinePlayer player, @NotNull String params) {
        if (player == null) return "";

        UUID uuid = player.getUniqueId();

        if (params.equalsIgnoreCase("prefix")) {
            return JustLevelAPI.getRealmPrefix(uuid);
        }

        if (params.equalsIgnoreCase("level")) {
            return String.valueOf(JustLevelAPI.getLevel(uuid));
        }

        if (params.equalsIgnoreCase("exp")) {
            return String.valueOf(JustLevelAPI.getRealmPrefix(uuid));
        }

        if (params.equalsIgnoreCase("upgrade_exp")) {
            return String.valueOf(JustLevelAPI.getUpgradeExp(uuid));
        }

        if (params.equalsIgnoreCase("stage")) {
            return String.valueOf(JustLevelAPI.getStage(uuid));
        }

        if (params.equalsIgnoreCase("realm")) {
            return String.valueOf(JustLevelAPI.getRealm(uuid));
        }

        if (params.equalsIgnoreCase("realm_name")) {
            return String.valueOf(JustLevelAPI.getRealmName(uuid));
        }

        if (params.equalsIgnoreCase("realm_points")) {
            return String.valueOf(JustLevelAPI.getRealmPoints(uuid));
        }

        if (params.equalsIgnoreCase("stage_points")) {
            return String.valueOf(JustLevelAPI.getStagePoints(uuid));
        }

        return "";
    }
}
