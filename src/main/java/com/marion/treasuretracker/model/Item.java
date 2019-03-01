package com.marion.treasuretracker.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "item", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SOURCE")
    private String source;

    @Column(name = "ITEMTYPE", nullable = false)
    private ItemType itemType;

    @Column(name = "ITEMSUBTYPE", nullable = false)
    private ItemSubType itemSubType = ItemSubType.none;

    @Column(name = "WEIGHT")
    private Float weight;

    @Column(name = "VALUE")
    private Float value;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;

    @Column(name = "WIDTH")
    private Float width;

    @Column(name = "LENGTH")
    private Float length;

    @Column(name = "HEIGHT")
    private Float height;

    @Column(name = "TAGS")
    private ArrayList<String> tags = new ArrayList<>();

    @JoinColumn(name = "CONTAINER")
    @ManyToOne
    private Container container;

    @Override
    public boolean equals(Object o) {
        if (o instanceof Item && this.id != null) {
            if (this.id.equals(((Item) o).id)) {
                return true;
            }
        }
        return false;
    }

    public Item copy() {
        Item item = new Item();
        item.name = this.name;
        item.description = this.description;
        item.source = this.source;
        item.itemType = this.itemType;
        item.itemSubType = this.itemSubType;
        item.weight = this.weight;
        item.value = this.value;
        item.amount = this.amount;
        item.height = this.height;
        item.width = this.width;
        item.length = this.length;
        item.container = this.container;
        return item;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getLength() {
        return length;
    }

    public void setLength(Float length) {
        this.length = length;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList tags) {
        this.tags = tags;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
