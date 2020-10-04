package com.marion.treasuretracker.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "itemtag", schema = "public")
public class ItemTag implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn
    private Item item;

    @ManyToOne
    @JoinColumn
    private Tag tag;

    public ItemTag() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item itemId) {
        this.item = itemId;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tagId) {
        this.tag = tagId;
    }
}
