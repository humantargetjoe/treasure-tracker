package com.marion.treasuretracker.model;

import java.util.ArrayList;
import java.util.List;

public class Totals {
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

    public class Valuable {
        private String name;
        private Float value = 0f;
        private Integer count = 0;
        private List<Float> denominations = new ArrayList<>();

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

        public List<Float> getDenominations() {
            return denominations;
        }

        public void setDenominations(List<Float> denominations) {
            this.denominations = denominations;
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

    private Coins coins = new Coins();
    private List<Valuable> jewelry = new ArrayList<>();
    private List<Valuable> gems = new ArrayList<>();

}
