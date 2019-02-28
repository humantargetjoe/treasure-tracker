package com.marion.treasuretracker.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "container", schema = "public")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    Integer id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

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
    @OneToMany
    private List<Item> items = new ArrayList<>();

    private Float currentWeight;

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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Float currentWeight) {
        this.currentWeight = currentWeight;
    }
}
