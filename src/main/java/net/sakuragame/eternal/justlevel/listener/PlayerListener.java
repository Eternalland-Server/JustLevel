package net.sakuragame.eternal.justlevel.listener;

import com.taylorswiftcn.megumi.uifactory.event.screen.UIFScreenOpenEvent;
import net.sakuragame.eternal.dragoncore.util.Pair;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.api.event.PlayerDataLoadEvent;
import net.sakuragame.eternal.justlevel.core.CardManager;
import net.sakuragame.eternal.justlevel.core.user.PlayerLevelData;
import net.sakuragame.eternal.justlevel.hook.ClientPlaceholder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final JustLevel plugin = JustLevel.getInstance();

    @EventHandler(priority = EventPriority.HIGH)
    public void onLogin(AsyncPlayerPreLoginEvent e) {
        if (e.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) return;
        UUID uuid = e.getUniqueId();
        Pair<String, Long> result = JustLevel.getStorageManager().getUseCard(uuid);
        if (result == null) return;

        CardManager.add(uuid, result.getKey(), result.getValue());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () -> {
            JustLevel.getUserManager().loadAccount(uuid);
            PlayerLevelData account = JustLevel.getUserManager().getAccount(uuid);
            if (account == null) {
                player.kickPlayer("账户未被正确加载，请重新进入。");
                plugin.getLogger().info("玩家 " + player.getName() + " 账户数据载入失败!");
            }
            else {
                account.updateVanillaInfo();

                PlayerDataLoadEvent event = new PlayerDataLoadEvent(player);
                event.call();
            }
        }, 10);
    }

    @EventHandler
    public void onOpen(UIFScreenOpenEvent e) {
        Player player = e.getPlayer();
        if (!e.getScreenID().equals("huds")) return;

        ClientPlaceholder.send(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> JustLevel.getUserManager().saveAccount(uuid));
    }

    @EventHandler
    public void onVanillaExpChange(PlayerExpChangeEvent e) {
        e.setAmount(0);
    }
}
