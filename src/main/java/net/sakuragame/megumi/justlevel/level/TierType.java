package net.sakuragame.megumi.justlevel.level;

public enum TierType {

    Stage("stage"),
    Realm("realm");

    private String id;

    TierType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public static TierType getType(String id) {
        for (TierType type : values()) {
            if (type.getId().equals(id)) return type;
        }

        return null;
    }
}
