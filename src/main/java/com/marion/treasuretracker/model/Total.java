package com.marion.treasuretracker.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Total {
    private static Log log = LogFactory.getLog(Total.class);

    public Valuable addGem(ItemSubType itemSubType) {
        Valuable gem = new Valuable();
        getGems().add(gem);
        gem.name = itemSubType.name();
        return gem;
    }

    public Valuable addJewelry(ItemSubType itemSubType) {
        Valuable jewelry = new Valuable();
        getJewelry().add(jewelry);
        jewelry.name = itemSubType.name();
        return jewelry;
    }

    public Valuable addOther(ItemSubType itemSubType) {
        Valuable other = new Valuable();
        getOther().add(other);
        other.name = itemSubType.name();
        return other;
    }

    public Valuable addTotal(ItemType itemType) {
        Valuable total = new Valuable();
        switch (itemType) {
            case gem:
                getGems().add(total);
                break;
            case jewelry:
                getJewelry().add(total);
                break;
            default:
                getOther().add(total);
                break;
        }
        total.name = "Total";
        return total;
    }

    public void formatTotals() {
        for (Valuable valuable : getGems()) {
            valuable.formatDenominations();
        }
        for (Valuable valuable : getJewelry()) {
            valuable.formatDenominations(true);
        }
        for (Valuable valuable : getOther()) {
            valuable.formatDenominations(true);
        }
    }

    public Coins getCoins() {
        return coins;
    }

    public void setCoins(Coins coins) {
        this.coins = coins;
    }

    public List<Valuable> getJewelry() {
        return jewelry;
    }

    public void setJewelry(List<Valuable> jewelry) {
        this.jewelry = jewelry;
    }

    public List<Valuable> getGems() {
        return gems;
    }

    public void setGems(List<Valuable> gems) {
        this.gems = gems;
    }

    public List<Valuable> getOther() {
        return other;
    }

    public void setOther(List<Valuable> other) {
        this.other = other;
    }

    public Float getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Float grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }

    public class Valuable {
        private String name;
        private Float value = 0f;
        private Integer count = 0;
        private Map<String, List<Float>> items = new HashMap<>();
        private List<String> denominations = new ArrayList<>();

        public Float getValue() {
            return value;
        }

        public void setValue(Float value) {
            this.value = value;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Map<String, List<Float>> getItems() {
            return items;
        }

        public void setItems(Map<String, List<Float>> items) {
            this.items = items;
        }

        public List<String> getDenominations() {
            return denominations;
        }

        public void setDenominations(List<String> denominations) {
            this.denominations = denominations;
        }

        protected void formatDenominations() {
            formatDenominations(false);
        }

        protected void formatDenominations(boolean displayName) {
            Map<Integer, Integer> countMap = new HashMap<>();
            Map<Integer, String> nameMap = new HashMap<>();

            for (Map.Entry<String, List<Float>> entry: getItems().entrySet()) {
                for (Float itemValue: entry.getValue()) {
                    Integer key = itemValue.intValue();
                    if (!countMap.containsKey(key)) {
                        countMap.put(key, 0);
                        nameMap.put(key, entry.getKey());
                    }
                    countMap.put(key, countMap.get(key) + 1);
                }
            }

            for (Map.Entry<Integer, Integer> entry : countMap.entrySet()) {
                if (displayName) {
                    getDenominations().add(String.format("%s: %sx %s", nameMap.get(entry.getKey()), entry.getValue(), entry.getKey()));
                } else {
                    getDenominations().add(String.format("%sx %s", entry.getValue(), entry.getKey()));
                }
            }
        }
    }

    public class Coins {
        private Float total = 0f;
        private Integer platinum = 0;
        private Integer gold = 0;
        private Integer electrum = 0;
        private Integer silver = 0;
        private Integer copper = 0;

        public Float getTotal() {
            return total;
        }

        public void setTotal(Float total) {
            this.total = total;
        }

        public Integer getPlatinum() {
            return platinum;
        }

        public void setPlatinum(Integer platinum) {
            this.platinum = platinum;
        }

        public Integer getGold() {
            return gold;
        }

        public void setGold(Integer gold) {
            this.gold = gold;
        }

        public Integer getElectrum() {
            return electrum;
        }

        public void setElectrum(Integer electrum) {
            this.electrum = electrum;
        }

        public Integer getSilver() {
            return silver;
        }

        public void setSilver(Integer silver) {
            this.silver = silver;
        }

        public Integer getCopper() {
            return copper;
        }

        public void setCopper(Integer copper) {
            this.copper = copper;
        }
    }

    private Float grandTotal;
    private Coins coins = new Coins();
    private List<Valuable> jewelry = new ArrayList<>();
    private List<Valuable> gems = new ArrayList<>();
    private List<Valuable> other = new ArrayList<>();

    private Container container;
}
