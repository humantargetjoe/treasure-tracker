package com.marion.treasuretracker.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DataValidatorTest {

    @Test
    public void validateCoins() {
        Item item = new Item();
        item.setName("Coins");
        item.setItemType(ItemType.coin);
        item.setAmount(1);
        for (ItemSubType itemSubType: Constants.COINS) {
            item.setItemSubType(itemSubType);
            Assert.assertTrue("Failed to validate " + itemSubType, DataValidator.validateCoins(item));
        }
    }

    @Test
    public void validateGems() {
        Item item = new Item();
        item.setName("Gems");
        item.setItemType(ItemType.gem);
        item.setAmount(1);
        for (ItemSubType itemSubType: Constants.GEMS) {
            item.setItemSubType(itemSubType);
            Assert.assertTrue("Failed to validate " + itemSubType, DataValidator.validateGems(item));
        }
    }

    @Test
    public void validateJewelry() {
        Item item = new Item();
        item.setName("Jewelry");
        item.setItemType(ItemType.jewelry);
        item.setAmount(1);
        for (ItemSubType itemSubType : Constants.JEWELRY) {
            item.setItemSubType(itemSubType);
            Assert.assertTrue("Failed to validate " + itemSubType, DataValidator.validateJewelry(item));
        }
    }
}