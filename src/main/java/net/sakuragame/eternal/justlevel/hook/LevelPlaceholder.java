package net.sakuragame.eternal.justlevel.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import net.sakuragame.eternal.justlevel.level.PlayerLevelData;
import net.sakuragame.eternal.justlevel.JustLevel;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class LevelPlaceholder extends PlaceholderExpansion {

    private final JustLevel plugin;

    public LevelPlaceholder() {
        this.plugin = JustLevel.getInstance();
    }

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
        PlayerLevelData data = plugin.getPlayerData().get(uuid);

        if (data == null) return "";

        if (params.equalsIgnoreCase("level")) {
            return String.valueOf(data.getLevel());
        }

        if (params.equalsIgnoreCase("total_level")) {
            return String.valueOf(data.getTotalLevel());
        }

        if (params.equalsIgnoreCase("exp")) {
            return String.valueOf(data.getExp());
        }

        if (params.equalsIgnoreCase("upgrade_exp")) {
            return String.valueOf(data.getUpgradeExp());
        }

        if (params.equalsIgnoreCase("stage")) {
            return String.valueOf(data.getStage());
        }

        if (params.equalsIgnoreCase("realm")) {
            return String.valueOf(data.getRealm());
        }

        if (params.equalsIgnoreCase("realm_name")) {
            return String.valueOf(data.getRealmName());
        }

        if (params.equalsIgnoreCase("stage_points")) {
            return String.valueOf(data.getStagePoints());
        }

        if (params.equalsIgnoreCase("realm_points")) {
            return String.valueOf(data.getRealmPoints());
        }

        return "";
    }
}
