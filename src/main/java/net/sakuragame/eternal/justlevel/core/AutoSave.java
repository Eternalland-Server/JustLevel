package net.sakuragame.eternal.justlevel.core;

import net.sakuragame.eternal.justlevel.api.JustLevelAPI;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class AutoSave extends BukkitRunnable {

    private final UUID uuid;

    public AutoSave(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void run() {
        PlayerLevelData account = JustLevelAPI.getUserData(this.uuid);
        if (account == null) {
            cancel();
            return;
        }
        account.saveData();
    }
}
