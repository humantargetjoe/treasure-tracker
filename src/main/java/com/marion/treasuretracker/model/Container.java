package com.marion.treasuretracker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="container", schema = "public")
public class Container {
    @Id
    private
    Long id;
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long maximumWeight;
    @Column(nullable = false)
    private Long maximumVolume;
    @Column(nullable = false)
    private Boolean isExtraDimensional;

    @Column
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

    public Long getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(Long maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public Long getMaximumVolume() {
        return maximumVolume;
    }

    public void setMaximumVolume(Long maximumVolume) {
        this.maximumVolume = maximumVolume;
    }

    public Boolean getExtraDimensional() {
        return isExtraDimensional;
    }

    public void setExtraDimensional(Boolean extraDimensional) {
        isExtraDimensional = extraDimensional;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }
}
