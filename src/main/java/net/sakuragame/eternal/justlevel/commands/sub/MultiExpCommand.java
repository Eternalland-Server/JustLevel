package net.sakuragame.eternal.justlevel.commands.sub;

import com.taylorswiftcn.justwei.commands.sub.SubCommand;
import com.taylorswiftcn.justwei.util.MegumiUtil;
import net.sakuragame.eternal.justlevel.JustLevel;
import net.sakuragame.eternal.justlevel.commands.CommandPerms;
import org.bukkit.command.CommandSender;

public class MultiExpCommand extends SubCommand {
    @Override
    public String getIdentifier() {
        return "multiExp";
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        if (args.length < 3) return;

        String s1 = args[0];
        String s2 = args[1];
        String s3 = args[2];

        if (!MegumiUtil.isFloat(s1)) return;
        if (!MegumiUtil.isNumber(s2)) return;

        double addition = Double.parseDouble(s1) - 1;
        int minute = Integer.parseInt(s2);

        JustLevel.getMultiExpManager().set(addition, minute, s3);
        JustLevel.getMultiExpManager().update(addition, minute, s3);
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
