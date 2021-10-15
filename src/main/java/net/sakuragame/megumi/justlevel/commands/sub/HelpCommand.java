package net.sakuragame.megumi.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.SubCommand;
import net.sakuragame.megumi.justlevel.file.sub.MessageFile;
import org.bukkit.command.CommandSender;

public class HelpCommand extends SubCommand {

    @Override
    public String getIdentifier() {
        return "help";
    }

    @Override
    public void perform(CommandSender CommandSender, String[] Strings) {
        MessageFile.help.forEach(CommandSender::sendMessage);
        if (CommandSender.hasPermission("justrpg.admin"))
            MessageFile.adminHelp.forEach(CommandSender::sendMessage);
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
