package com.taylorswiftcn.megumi.justlevel.commands;

import com.taylorswiftcn.justwei.commands.ICommand;
import com.taylorswiftcn.megumi.justlevel.commands.sub.HelpCommand;
import com.taylorswiftcn.megumi.justlevel.commands.sub.ReloadCommand;

public class MainCommand extends ICommand {

    public MainCommand() {
        this.help = new HelpCommand();
        this.commands.put("reload", new ReloadCommand());
    }
}
