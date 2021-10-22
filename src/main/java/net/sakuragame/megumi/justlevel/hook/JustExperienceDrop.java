package net.sakuragame.megumi.justlevel.hook;

import io.lumine.xikage.mythicmobs.adapters.AbstractPlayer;
import io.lumine.xikage.mythicmobs.adapters.bukkit.BukkitAdapter;
import io.lumine.xikage.mythicmobs.drops.Drop;
import io.lumine.xikage.mythicmobs.drops.DropMetadata;
import io.lumine.xikage.mythicmobs.drops.IIntangibleDrop;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import net.sakuragame.megumi.justlevel.api.JustLevelAPI;
import org.bukkit.entity.Player;

public class JustExperienceDrop extends Drop implements IIntangibleDrop {

    public JustExperienceDrop(MythicLineConfig config) {
        super(config.getLine(), config);
    }

    @Override
    public void giveDrop(AbstractPlayer abstractPlayer, DropMetadata dropMetadata) {
        Player player = BukkitAdapter.adapt(abstractPlayer);
        JustLevelAPI.addExp(player, getAmount());
    }
}
