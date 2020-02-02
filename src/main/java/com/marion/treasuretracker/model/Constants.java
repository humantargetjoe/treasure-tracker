package com.marion.treasuretracker.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Constants {
    public static float poundsPerCoin = 1f / 50f;
    public static float goldPerPlatinum = 10f;
    public static float goldPerElectrum = 0.2f;
    public static float goldPerSilver = 0.1f;
    public static float goldPerCopper = 0.01f;

    private static ItemSubType[] COINS = {
            ItemSubType.platinum,
            ItemSubType.gold,
            ItemSubType.electrum,
            ItemSubType.silver,
            ItemSubType.copper,
    };

    private static ItemSubType[] GEMS = {
            ItemSubType.unspecified,
            ItemSubType.agate,
            ItemSubType.amber,
            ItemSubType.amethyst,
            ItemSubType.aquamarine,
            ItemSubType.aventurine,
            ItemSubType.azurite,
            ItemSubType.bandedAgate,
            ItemSubType.bloodstone,
            ItemSubType.carnelians,
            ItemSubType.chalcedony,
            ItemSubType.chrysoberyl,
            ItemSubType.citrine,
            ItemSubType.coral,
            ItemSubType.diamond,
            ItemSubType.garnet,
            ItemSubType.goldenBeryl,
            ItemSubType.hematite,
            ItemSubType.hessonite,
            ItemSubType.jade,
            ItemSubType.jasper,
            ItemSubType.jet,
            ItemSubType.kunzite,
            ItemSubType.malachite,
            ItemSubType.moonstone,
            ItemSubType.onyx,
            ItemSubType.pearl,
            ItemSubType.peridot,
            ItemSubType.quartz,
            ItemSubType.roseQuartz,
            ItemSubType.ruby,
            ItemSubType.sapphire,
            ItemSubType.spinel,
            ItemSubType.tigerseye,
            ItemSubType.tourmaline,
            ItemSubType.turquoise,
            ItemSubType.zircon,
    };

    private static ItemSubType[] JEWELRY = {
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

    private static ItemSubType[] OTHER = {
            ItemSubType.unspecified,
            ItemSubType.none,
            ItemSubType.gold
    };

    public static List<ItemSubType> Gems = Collections.unmodifiableList(Arrays.asList(GEMS));
    public static List<ItemSubType> Coins = Collections.unmodifiableList(Arrays.asList(COINS));
    public static List<ItemSubType> Jewelry = Collections.unmodifiableList(Arrays.asList(JEWELRY));
    public static List<ItemSubType> Other = Collections.unmodifiableList(Arrays.asList(OTHER));
}
