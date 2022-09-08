package net.sakuragame.eternal.justlevel.listener.hook;

import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicDropLoadEvent;
import io.lumine.xikage.mythicmobs.drops.Drop;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.hook.JustExperienceDrop;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MythicMobListener implements Listener {

    private final JustLevel plugin = JustLevel.getInstance();

    @EventHandler
    public void onDropLoad(MythicDropLoadEvent e) {
        String dropName = e.getDropName();
        if (!dropName.equalsIgnoreCase("jexp")) return;

        Drop drop = new JustExperienceDrop(e.getConfig());
        e.register(drop);
    }
}
