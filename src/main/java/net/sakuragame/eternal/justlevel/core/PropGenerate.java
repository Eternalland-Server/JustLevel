package net.sakuragame.eternal.justlevel.core;

import ink.ptms.zaphkiel.ZaphkielAPI;
import ink.ptms.zaphkiel.api.ItemStream;
import ink.ptms.zaphkiel.taboolib.module.nms.ItemTag;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class PropGenerate {

    @Getter
    public enum Item {

        EXP(0, "exp_item", "&e"),
        LEVEL(1, "level_item", "&e"),
        MONEY(2, "money_item", "&a"),
        COINS(3, "coins_item", "&b");

        private final int index;
        private final String ID;
        private final String color;

        Item(int index, String ID, String color) {
            this.index = index;
            this.ID = ID;
            this.color = color;
        }

        public static Item match(int index) {
            for (Item item : values()) {
                if (item.getIndex() != index) continue;
                return item;
            }

            return null;
        }
    }

    public void spawnExpProp(Location location, int value, int amount) {
        this.generate(0, location, value, amount);
    }

    public void spawnLevelProp(Location location, int value, int amount) {
        this.generate(1, location, value, amount);
    }

    public void spawnMoneyProp(Location location, int value, int amount) {
        this.generate(2, location, value, amount);
    }

    public void spawnCoinsProp(Location location, int value, int amount) {
        this.generate(3, location, value, amount);
    }

    public void generate(int type, Location location, int value, int amount) {
        Item item = Item.match(type);
        if (item == null) return;

        ItemStream itemStream = ZaphkielAPI.INSTANCE.getItem(item.getID(), null);
        if (itemStream == null) return;
        ItemTag itemTag = itemStream.getZaphkielData();
        itemTag.putDeep("parkour.type", type);
        itemTag.putDeep("parkour.value", value);

        ItemStack itemStack = itemStream.rebuildToItemStack(null);
        itemStack.setAmount(amount);

        location.getWorld().dropItem(location, itemStack);
    }
}
