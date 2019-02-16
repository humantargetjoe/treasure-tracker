package com.marion.treasuretracker.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "container", schema = "public")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "MAX_WEIGHT", nullable = false)
    private Float maximumWeight;

    @Column(name = "MAX_VOLUME", nullable = false)
    private Float maximumVolume;

    @Column(name = "IS_EXTRADIMENSIONAL", nullable = false)
    private Boolean isExtraDimensional;

    @Column(name = "WEIGHT", nullable = false)
    private Float weight;

    @Column(name = "WIDTH", nullable = false)
    private Float width;

    @Column(name = "LENGTH", nullable = false)
    private Float length;

    @Column(name = "HEIGHT", nullable = false)
    private Float height;

    @Column(name = "ITEMS")
    private ArrayList<Item> items;

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

    public Float getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(Float maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public Float getMaximumVolume() {
        return maximumVolume;
    }

    public void setMaximumVolume(Float maximumVolume) {
        this.maximumVolume = maximumVolume;
    }

    public Boolean getIsExtraDimensional() {
        return isExtraDimensional;
    }

    public void setIsExtraDimensional(Boolean extraDimensional) {
        isExtraDimensional = extraDimensional;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
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
}
