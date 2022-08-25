package net.sakuragame.eternal.justlevel.core.level;

import lombok.Getter;

@Getter
public enum Define {

    Level("level", 100),
    Stage("stage", 10),
    Realm("realm", 21),
    Daily("daily", 21000000);

    private final String id;
    private final int max;

    Define(String id, int max) {
        this.id = id;
        this.max = max;
    }

    public static Define match(String id) {
        for (Define define : values()) {
            if (define.getId().equals(id)) return define;
        }

        return null;
    }
}
