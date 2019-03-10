package com.marion.treasuretracker.model;

public class Queries {
    public static String ITEMS_IN_CONTAINER = "SELECT * FROM item WHERE item.CONTAINER = %d AND item.AMOUNT > 0";
    public static String GOLD_COINS_IN_CONTAINER = "SELECT * FROM item " +
            "WHERE item.CONTAINER = %d AND item.ITEMTYPE = 'coin' AND item.ITEMSUBTYPE = 'gold'";
    public static String ITEMS_BY_TYPE = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND item.AMOUNT > 0";
    public static String ITEMS_BY_TYPE_AND_SUBTYPE = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND ITEMSUBTYPE = '%s' AND item.AMOUNT > 0";
}
