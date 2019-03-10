package com.marion.treasuretracker.model;

public class Constants {
    public static float poundsPerCoin = 1f / 50f;
    public static float goldPerPlatinum = 10f;
    public static float goldPerElectrum = 0.5f;
    public static float goldPerSilver = 0.1f;
    public static float goldPerCopper = 0.01f;

    public static ItemSubType[] COINS = {
            ItemSubType.platinum,
            ItemSubType.gold,
            ItemSubType.electrum,
            ItemSubType.silver,
            ItemSubType.copper,
    };

    public static ItemSubType[] GEMS = {
            ItemSubType.unspecified,
            ItemSubType.agate,
            ItemSubType.amber,
            ItemSubType.amethyst,
            ItemSubType.azurite,
            ItemSubType.bandedAgate,
            ItemSubType.bloodstone,
            ItemSubType.carnelians,
            ItemSubType.chrysoberyl,
            ItemSubType.coral,
            ItemSubType.diamond,
            ItemSubType.garnet,
            ItemSubType.hematite,
            ItemSubType.jade,
            ItemSubType.jasper,
            ItemSubType.jet,
            ItemSubType.moonstone,
            ItemSubType.onyx,
            ItemSubType.pearl,
            ItemSubType.quartz,
            ItemSubType.ruby,
            ItemSubType.sapphire,
            ItemSubType.tigerseye,
            ItemSubType.tourmaline,
            ItemSubType.turquoise,
            ItemSubType.zircon,
    };

    public static ItemSubType[] JEWELRY = {
            ItemSubType.unspecified,
            ItemSubType.amulet,
            ItemSubType.armlet,
            ItemSubType.beltbuckle,
            ItemSubType.bracelet,
            ItemSubType.brooch,
            ItemSubType.earring,
            ItemSubType.necklace,
            ItemSubType.nosering,
            ItemSubType.pin,
            ItemSubType.ring,
    };

    public static ItemSubType[] OTHER = {
            ItemSubType.unspecified,
            ItemSubType.none,
            ItemSubType.gold
    };
}
