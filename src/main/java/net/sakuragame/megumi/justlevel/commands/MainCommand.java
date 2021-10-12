package net.sakuragame.megumi.justlevel.commands;

import com.taylorswiftcn.justwei.commands.ICommand;
import net.sakuragame.megumi.justlevel.commands.sub.HelpCommand;
import net.sakuragame.megumi.justlevel.commands.sub.InfoCommand;
import net.sakuragame.megumi.justlevel.commands.sub.ItemCommand;
import net.sakuragame.megumi.justlevel.commands.sub.ReloadCommand;
import net.sakuragame.megumi.justlevel.commands.sub.player.AddExpCommand;
import net.sakuragame.megumi.justlevel.commands.sub.player.SetLevelCommand;
import net.sakuragame.megumi.justlevel.commands.sub.player.SetRealmCommand;
import net.sakuragame.megumi.justlevel.commands.sub.player.SetStageCommand;

public class MainCommand extends ICommand {

    public MainCommand() {
        help = new HelpCommand();
        commands.put("info", new InfoCommand());
        commands.put("item", new ItemCommand());
        commands.put("addExp", new AddExpCommand());
        commands.put("setLevel", new SetLevelCommand());
        commands.put("setStage", new SetStageCommand());
        commands.put("setRealm", new SetRealmCommand());
        commands.put("reload", new ReloadCommand());
    }
}
