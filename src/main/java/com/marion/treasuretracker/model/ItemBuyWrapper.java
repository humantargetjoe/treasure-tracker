package com.marion.treasuretracker.model;

public class ItemBuyWrapper {

    public ItemBuyWrapper(Item item) {
        this.setItem(item);
    }

    private Item item;
    private Integer amount;
    private ItemType itemType;
    private ItemSubType itemSubType;


    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public ItemSubType getItemSubType() {
        return itemSubType;
    }

    public void setItemSubType(ItemSubType itemSubType) {
        this.itemSubType = itemSubType;
    }
}
