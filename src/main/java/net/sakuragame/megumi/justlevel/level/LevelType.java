package net.sakuragame.megumi.justlevel.level;

public enum LevelType {

    Stage("stage"),
    Realm("realm");

    private String id;

    LevelType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static LevelType getType(String id) {
        for (LevelType type : values()) {
            if (type.getId().equals(id)) return type;
        }

        return null;
    }
}
