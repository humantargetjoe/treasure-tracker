package com.marion.treasuretracker.model;

public class ItemMoveWrapper {

    public ItemMoveWrapper(Item item) {
        this.setItem(item);
    }

    private Item item;
    private Integer amount;


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

}
