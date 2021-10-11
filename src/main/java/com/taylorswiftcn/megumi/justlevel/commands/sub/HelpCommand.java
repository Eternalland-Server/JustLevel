package com.taylorswiftcn.megumi.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.SubCommand;
import com.taylorswiftcn.megumi.justlevel.file.sub.MessageFile;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {

    @Override
    public void perform(CommandSender CommandSender, String[] Strings) {
        MessageFile.Help.forEach(CommandSender::sendMessage);
        if (CommandSender.hasPermission("justrpg.admin"))
            MessageFile.AdminHelp.forEach(CommandSender::sendMessage);
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
