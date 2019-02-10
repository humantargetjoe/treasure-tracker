package com.marion.treasuretracker.model;

public class ItemValidator {

    /**
     * ItemType & Subtype constraints for Coins
     *
     * @param item
     * @return
     */
    public static boolean validateCoins(Item item) {
        return
                item.getItemType() == ItemType.coin && (
                        item.getItemSubType() == ItemSubType.gold ||
                                item.getItemSubType() == ItemSubType.silver ||
                                item.getItemSubType() == ItemSubType.platinum ||
                                item.getItemSubType() == ItemSubType.copper ||
                                item.getItemSubType() == ItemSubType.electrum);
    }

    /**
     * ItemType & Subtype constraints for Gems
     *
     * @param item
     * @return
     */
    public static boolean validateGems(Item item) {
        return
                item.getItemType() == ItemType.gem && (
                        item.getItemSubType() == ItemSubType.agate ||
                                item.getItemSubType() == ItemSubType.amber ||
                                item.getItemSubType() == ItemSubType.amethyst ||
                                item.getItemSubType() == ItemSubType.azurite ||
                                item.getItemSubType() == ItemSubType.bandedAgate ||
                                item.getItemSubType() == ItemSubType.bloodstone ||
                                item.getItemSubType() == ItemSubType.carnelians ||
                                item.getItemSubType() == ItemSubType.chrysoberyl ||
                                item.getItemSubType() == ItemSubType.coral ||
                                item.getItemSubType() == ItemSubType.diamond ||
                                item.getItemSubType() == ItemSubType.garnet ||
                                item.getItemSubType() == ItemSubType.hematite ||
                                item.getItemSubType() == ItemSubType.jade ||
                                item.getItemSubType() == ItemSubType.jasper ||
                                item.getItemSubType() == ItemSubType.jet ||
                                item.getItemSubType() == ItemSubType.moonstone ||
                                item.getItemSubType() == ItemSubType.onyx ||
                                item.getItemSubType() == ItemSubType.pearl ||
                                item.getItemSubType() == ItemSubType.quartz ||
                                item.getItemSubType() == ItemSubType.sapphire ||
                                item.getItemSubType() == ItemSubType.tigerseye ||
                                item.getItemSubType() == ItemSubType.tourmaline ||
                                item.getItemSubType() == ItemSubType.turquoise ||
                                item.getItemSubType() == ItemSubType.zircon);
    }

    /**
     * ItemType & Subtype constraints for Jewelry
     *
     * @param item
     * @return
     */
    public static boolean validateJewelry(Item item) {
        return
                item.getItemType() == ItemType.jewelry && (
                        item.getItemSubType() == ItemSubType.amulet ||
                                item.getItemSubType() == ItemSubType.armlet ||
                                item.getItemSubType() == ItemSubType.beltbuckle ||
                                item.getItemSubType() == ItemSubType.bracelet ||
                                item.getItemSubType() == ItemSubType.brooch ||
                                item.getItemSubType() == ItemSubType.earring ||
                                item.getItemSubType() == ItemSubType.necklace ||
                                item.getItemSubType() == ItemSubType.nosering ||
                                item.getItemSubType() == ItemSubType.pin ||
                                item.getItemSubType() == ItemSubType.ring);
    }
}
