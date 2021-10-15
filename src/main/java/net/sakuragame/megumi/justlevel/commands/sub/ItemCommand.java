package net.sakuragame.megumi.justlevel.commands.sub;

import net.sakuragame.megumi.justlevel.commands.MegumiCommand;
import net.sakuragame.megumi.justlevel.commands.sub.item.GetCommand;
import net.sakuragame.megumi.justlevel.commands.sub.item.GiveCommand;

public class ItemCommand extends MegumiCommand {

    public ItemCommand() {
        this.commands.put("give", new GiveCommand());
        this.commands.put("get", new GetCommand());
    }

    @Override
    public String getIdentifier() {
        return "item";
    }
}
