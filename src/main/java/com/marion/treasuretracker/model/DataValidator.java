package com.marion.treasuretracker.model;

public class DataValidator {

    /**
     * Validates the non-nullable fields for a Container
     *
     * @param container - container to validate
     * @return true if the jewelry has the correct typing information
     */
    public static boolean validateContainer(Container container) {
        return
                container.getName() != null &&
                        container.getHeight() != null &&
                        container.getWidth() != null &&
                        container.getLength() != null &&
                        container.getWeight() != null &&
                        container.getMaximumWeight() != null &&
                        container.getMaximumVolume() != null &&
                        container.getIsExtraDimensional() != null;
    }

    /**
     * Validates the non-nullable fields for an Item
     *
     * @param item - item to validate
     * @return true if the jewelry has the correct typing information
     */
    public static boolean validateItem(Item item) {
        return
                item.getName() != null &&
                        item.getItemType() != null &&
                        item.getItemSubType() != null &&
                        item.getAmount() != null;
    }

    /**
     * ItemType & Subtype constraints for Coins
     *
     * @param item- item to validate
     * @return true if the jewelry has the correct typing information
     */
    public static boolean validateCoins(Item item) {
        return
                validateItem(item) &&
                        item.getItemType() == ItemType.coin && (
                        Constants.Coins.contains(item.getItemSubType()));
    }

    /**
     * ItemType & Subtype constraints for Gems
     *
     * @param item- item to validate
     * @return true if the jewelry has the correct typing information
     */
    public static boolean validateGems(Item item) {
        return
                validateItem(item) &&
                        item.getItemType() == ItemType.gem && (
                        item.getItemSubType() == ItemSubType.unspecified ||
                                Constants.Gems.contains(item.getItemSubType()));
    }

    /**
     * ItemType & Subtype constraints for Jewelry
     *
     * @param item - item to validate
     * @return true if the jewelry has the correct typing information
     */
    public static boolean validateJewelry(Item item) {
        return
                validateItem(item) &&
                        item.getItemType() == ItemType.jewelry && (
                        item.getItemSubType() == ItemSubType.unspecified ||
                                Constants.Jewelry.contains(item.getItemSubType()));
    }
}
