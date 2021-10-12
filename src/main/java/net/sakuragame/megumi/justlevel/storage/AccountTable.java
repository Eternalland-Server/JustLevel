package net.sakuragame.megumi.justlevel.storage;

public enum AccountTable {

    JUST_LEVEL_ACCOUNT(new DatabaseTable("justlevel_account",
            new String[] {
                    "`uuid` varchar(36) NOT NULL PRIMARY KEY",
                    "`player` varchar(48) NOT NULL",
                    "`level` int default 1",
                    "`exp` double default 0",
                    "`stage_point` int default 0",
                    "`realm_point` int default 0"
            }));

    private DatabaseTable table;

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
