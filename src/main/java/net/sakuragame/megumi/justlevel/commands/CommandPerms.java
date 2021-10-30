package net.sakuragame.megumi.justlevel.commands;

import lombok.Getter;

public enum CommandPerms {

    USER("justlevel.user"),
    ADMIN("justlevel.admin");

    @Getter
    private final String node;

    CommandPerms(String node) {
        this.node = node;
    }
}
