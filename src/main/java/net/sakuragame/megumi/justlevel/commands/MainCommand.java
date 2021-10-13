package net.sakuragame.megumi.justlevel.commands;

import com.taylorswiftcn.justwei.commands.ICommand;
import net.sakuragame.megumi.justlevel.commands.sub.*;
import net.sakuragame.megumi.justlevel.commands.sub.player.AddExpCommand;
import net.sakuragame.megumi.justlevel.commands.sub.player.SetLevelCommand;
import net.sakuragame.megumi.justlevel.commands.sub.player.SetRealmCommand;
import net.sakuragame.megumi.justlevel.commands.sub.player.SetStageCommand;

public class MainCommand extends ICommand {

    public MainCommand() {
        this.help = new HelpCommand();
        this.commands.put("info", new InfoCommand());
        this.commands.put("item", new ItemCommand());
        this.commands.put("addexp", new AddExpCommand());
        this.commands.put("setlevel", new SetLevelCommand());
        this.commands.put("setstage", new SetStageCommand());
        this.commands.put("setrealm", new SetRealmCommand());
        this.commands.put("points", new PointsCommand());
        this.commands.put("reload", new ReloadCommand());
    }
}
