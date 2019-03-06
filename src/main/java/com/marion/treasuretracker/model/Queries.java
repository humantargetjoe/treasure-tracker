package com.marion.treasuretracker.model;

public class Queries {
    public static String ITEMS_IN_CONTAINER = "SELECT * FROM item WHERE item.CONTAINER = %d";
    public static String ITEMS_BY_TYPE = "SELECT * FROM item WHERE ITEMTYPE = '%s'";
    public static String ITEMS_BY_TYPE_AND_SUBTYPE = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND ITEMSUBTYPE = '%s'";
    public static String TAGS_BY_NAME = "SELECT * FROM tag WHERE name = '%s'";
}
