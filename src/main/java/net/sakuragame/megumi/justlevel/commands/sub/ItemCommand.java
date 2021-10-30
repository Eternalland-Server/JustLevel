package net.sakuragame.megumi.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubTabCompleter;
import eos.moe.dragoncore.commands.sub.HelpCommand;
import net.sakuragame.megumi.justlevel.commands.CommandPerms;
import net.sakuragame.megumi.justlevel.commands.sub.item.GetCommand;
import net.sakuragame.megumi.justlevel.commands.sub.item.GiveCommand;

public class ItemCommand extends SubTabCompleter {

    public ItemCommand() {
        super(new HelpCommand());
        this.register(new GiveCommand());
        this.register(new GetCommand());
    }

    @Override
    public String getIdentifier() {
        return "item";
    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return CommandPerms.ADMIN.getNode();
    }
}
