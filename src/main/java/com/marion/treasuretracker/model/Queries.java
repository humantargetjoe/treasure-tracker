package com.marion.treasuretracker.model;

public class Queries {
    public static String ITEMS_IN_CONTAINER = "SELECT * FROM item WHERE CONTAINER = %d AND AMOUNT > 0";
    public static String GOLD_COINS_IN_CONTAINER = "SELECT * FROM item " +
            "WHERE CONTAINER = %d AND ITEMTYPE = 'coin' AND ITEMSUBTYPE = 'gold'";
    public static String ITEMS_BY_TYPE = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND AMOUNT > 0";
    public static String ITEMS_BY_TYPE_AND_SUBTYPE = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND ITEMSUBTYPE = '%s' AND AMOUNT > 0";
    public static String ITEMS_BY_TYPE_IN_CONTAINER = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND CONTAINER = %d AND AMOUNT > 0";
    public static String ITEMS_BY_TYPE_AND_SUBTYPE_IN_CONTAINER = "SELECT * FROM item WHERE ITEMTYPE = '%s' AND ITEMSUBTYPE = '%s' AND CONTAINER = %d AND AMOUNT > 0";
    public static String ITEMS_WHERE_ID_IN = "SELECT * FROM item WHERE id IN (%S) AND AMOUNT > 0";
    public static String TAG_BY_NAME = "SELECT * FROM tag WHERE NAME = '%s'";
    public static String TAGS_WHERE_ID_IN = "SELECT * FROM tag WHERE id IN (%S)";

    public static String ITEMTAGS_BY_ITEM = "SELECT * FROM itemtag WHERE item_id = %d";
    public static String ITEMTAGS_BY_TAG = "SELECT * FROM itemtag WHERE tag_id = %d";

}
