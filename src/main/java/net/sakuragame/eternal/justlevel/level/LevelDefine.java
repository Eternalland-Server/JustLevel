package net.sakuragame.eternal.justlevel.level;

public enum LevelDefine {

    Stage("stage"),
    Realm("realm");

    private final String id;

    LevelDefine(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static LevelDefine get(String id) {
        for (LevelDefine define : values()) {
            if (define.getId().equals(id)) return define;
        }

        return null;
    }
}
