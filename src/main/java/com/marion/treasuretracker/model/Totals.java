package com.marion.treasuretracker.model;

public class Totals {
    public Coins getCoins() {
        return coins;
    }

    public void setCoins(Coins coins) {
        this.coins = coins;
    }

    public Jewelry getJewelry() {
        return jewelry;
    }

    public void setJewelry(Jewelry jewelry) {
        this.jewelry = jewelry;
    }

    public Gems getGems() {
        return gems;
    }

    public void setGems(Gems gems) {
        this.gems = gems;
    }

    public class Pair {
        private Float value = 0f;
        private Integer count = 0;

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

    public class Jewelry {
        private Pair total = new Pair();
        private Pair amulet = new Pair();
        private Pair armlet = new Pair();
        private Pair beltbuckle = new Pair();
        private Pair bracelet = new Pair();
        private Pair brooch = new Pair();
        private Pair earring = new Pair();
        private Pair necklace = new Pair();
        private Pair nosering = new Pair();
        private Pair pin = new Pair();
        private Pair ring = new Pair();

        public Pair getAmulet() {
            return amulet;
        }

        public void setAmulet(Pair amulet) {
            this.amulet = amulet;
        }

        public Pair getArmlet() {
            return armlet;
        }

        public void setArmlet(Pair armlet) {
            this.armlet = armlet;
        }

        public Pair getBeltbuckle() {
            return beltbuckle;
        }

        public void setBeltbuckle(Pair beltbuckle) {
            this.beltbuckle = beltbuckle;
        }

        public Pair getBracelet() {
            return bracelet;
        }

        public void setBracelet(Pair bracelet) {
            this.bracelet = bracelet;
        }

        public Pair getBrooch() {
            return brooch;
        }

        public void setBrooch(Pair brooch) {
            this.brooch = brooch;
        }

        public Pair getEarring() {
            return earring;
        }

        public void setEarring(Pair earring) {
            this.earring = earring;
        }

        public Pair getNecklace() {
            return necklace;
        }

        public void setNecklace(Pair necklace) {
            this.necklace = necklace;
        }

        public Pair getNosering() {
            return nosering;
        }

        public void setNosering(Pair nosering) {
            this.nosering = nosering;
        }

        public Pair getPin() {
            return pin;
        }

        public void setPin(Pair pin) {
            this.pin = pin;
        }

        public Pair getRing() {
            return ring;
        }

        public void setRing(Pair ring) {
            this.ring = ring;
        }
    }

    public class Gems {
        private Pair total = new Pair();
        private Pair agate = new Pair();
        private Pair amber = new Pair();
        private Pair amethyst = new Pair();
        private Pair azurite = new Pair();
        private Pair bandedAgate = new Pair();
        private Pair bloodstone = new Pair();
        private Pair carnelians = new Pair();
        private Pair chrysoberyl = new Pair();
        private Pair coral = new Pair();
        private Pair diamond = new Pair();
        private Pair garnet = new Pair();
        private Pair hematite = new Pair();
        private Pair jade = new Pair();
        private Pair jasper = new Pair();
        private Pair jet = new Pair();
        private Pair moonstone = new Pair();
        private Pair onyx = new Pair();
        private Pair pearl = new Pair();
        private Pair quartz = new Pair();
        private Pair sapphire = new Pair();
        private Pair tigerseye = new Pair();
        private Pair tourmaline = new Pair();
        private Pair turquoise = new Pair();
        private Pair zircon = new Pair();

        public Pair getAgate() {
            return agate;
        }

        public void setAgate(Pair agate) {
            this.agate = agate;
        }

        public Pair getAmber() {
            return amber;
        }

        public void setAmber(Pair amber) {
            this.amber = amber;
        }

        public Pair getAmethyst() {
            return amethyst;
        }

        public void setAmethyst(Pair amethyst) {
            this.amethyst = amethyst;
        }

        public Pair getAzurite() {
            return azurite;
        }

        public void setAzurite(Pair azurite) {
            this.azurite = azurite;
        }

        public Pair getBandedAgate() {
            return bandedAgate;
        }

        public void setBandedAgate(Pair bandedAgate) {
            this.bandedAgate = bandedAgate;
        }

        public Pair getBloodstone() {
            return bloodstone;
        }

        public void setBloodstone(Pair bloodstone) {
            this.bloodstone = bloodstone;
        }

        public Pair getCarnelians() {
            return carnelians;
        }

        public void setCarnelians(Pair carnelians) {
            this.carnelians = carnelians;
        }

        public Pair getChrysoberyl() {
            return chrysoberyl;
        }

        public void setChrysoberyl(Pair chrysoberyl) {
            this.chrysoberyl = chrysoberyl;
        }

        public Pair getCoral() {
            return coral;
        }

        public void setCoral(Pair coral) {
            this.coral = coral;
        }

        public Pair getDiamond() {
            return diamond;
        }

        public void setDiamond(Pair diamond) {
            this.diamond = diamond;
        }

        public Pair getGarnet() {
            return garnet;
        }

        public void setGarnet(Pair garnet) {
            this.garnet = garnet;
        }

        public Pair getHematite() {
            return hematite;
        }

        public void setHematite(Pair hematite) {
            this.hematite = hematite;
        }

        public Pair getJade() {
            return jade;
        }

        public void setJade(Pair jade) {
            this.jade = jade;
        }

        public Pair getJasper() {
            return jasper;
        }

        public void setJasper(Pair jasper) {
            this.jasper = jasper;
        }

        public Pair getJet() {
            return jet;
        }

        public void setJet(Pair jet) {
            this.jet = jet;
        }

        public Pair getMoonstone() {
            return moonstone;
        }

        public void setMoonstone(Pair moonstone) {
            this.moonstone = moonstone;
        }

        public Pair getOnyx() {
            return onyx;
        }

        public void setOnyx(Pair onyx) {
            this.onyx = onyx;
        }

        public Pair getPearl() {
            return pearl;
        }

        public void setPearl(Pair pearl) {
            this.pearl = pearl;
        }

        public Pair getQuartz() {
            return quartz;
        }

        public void setQuartz(Pair quartz) {
            this.quartz = quartz;
        }

        public Pair getSapphire() {
            return sapphire;
        }

        public void setSapphire(Pair sapphire) {
            this.sapphire = sapphire;
        }

        public Pair getTigerseye() {
            return tigerseye;
        }

        public void setTigerseye(Pair tigerseye) {
            this.tigerseye = tigerseye;
        }

        public Pair getTourmaline() {
            return tourmaline;
        }

        public void setTourmaline(Pair tourmaline) {
            this.tourmaline = tourmaline;
        }

        public Pair getTurquoise() {
            return turquoise;
        }

        public void setTurquoise(Pair turquoise) {
            this.turquoise = turquoise;
        }

        public Pair getZircon() {
            return zircon;
        }

        public void setZircon(Pair zircon) {
            this.zircon = zircon;
        }

        public Pair getTotal() {
            return total;
        }

        public void setTotal(Pair total) {
            this.total = total;
        }
    }

    private Coins coins = new Coins();
    private Jewelry jewelry = new Jewelry();
    private Gems gems = new Gems();

}
