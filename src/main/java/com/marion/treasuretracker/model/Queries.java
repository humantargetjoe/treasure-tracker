package com.marion.treasuretracker.model;

public class Queries {
    public static String ITEMS_IN_CONTAINER = "SELECT * FROM item WHERE CONTAINER = %d AND AMOUNT > 0";
    public static String GOLD_COINS_IN_CONTAINER = "SELECT * FROM item " +
            "WHERE CONTAINER = %d AND ITEMTYPE = 'coin' AND ITEMSUBTYPE = 'gold'";
    public static String ITEMS_BY_TYPE = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND AMOUNT > 0";
    public static String ITEMS_BY_TYPE_AND_SUBTYPE = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND ITEMSUBTYPE = '%s' AND AMOUNT > 0";
    public static String ITEMS_BY_TYPE_IN_CONTAINER = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND CONTAINER = %d AND AMOUNT > 0";
    public static String ITEMS_BY_TYPE_AND_SUBTYPE_IN_CONTAINER = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND ITEMSUBTYPE = '%s' AND CONTAINER = %d";
}
