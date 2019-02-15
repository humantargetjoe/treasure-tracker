package com.marion.treasuretracker.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "item", schema = "public")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    Long id;

    @Column(nullable = false)
    private
    String name;

    @Column
    private
    String description;

    @Column
    private
    String source;

    @Column(nullable = false)
    private
    ItemType itemType;

    @Column(nullable = false)
    private
    ItemSubType itemSubType = ItemSubType.none;

    @Column
    private
    Integer weight;

    @Column
    private
    Integer value;

    @Column(nullable = false)
    private
    Integer amount;

    @Column
    private
    Integer width;

    @Column
    private
    Integer length;

    @Column
    private
    Integer height;

    @Column
    private
    ArrayList<String> tags = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (o instanceof Item && this.id != null) {
            if (this.id.equals(((Item)o).id)) {
                return true;
            }
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
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
}
