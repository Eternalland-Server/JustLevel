package com.taylorswiftcn.megumi.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {
    @Override
    public void perform(CommandSender CommandSender, String[] Strings) {

    }

    @Override
    public boolean playerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return null;
    }
}
