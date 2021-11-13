package net.sakuragame.eternal.justlevel.storage;

public enum AccountTable {

    JUST_LEVEL_ACCOUNT(new DatabaseTable("justlevel_account",
            new String[] {
                    "`uid` int NOT NULL PRIMARY KEY",
                    "`level` int default 0",
                    "`exp` double default 0",
                    "`stage_point` int default 0",
                    "`realm_point` int default 0"
            }));

    private final DatabaseTable table;

    AccountTable(DatabaseTable table) {
        this.table = table;
    }

    public String getTableName() {
        return table.getTableName();
    }

    public String[] getColumns() {
        return table.getTableColumns();
    }

    public DatabaseTable getTable() {
        return table;
    }

    public void createTable() {
        table.createTable();
    }
}