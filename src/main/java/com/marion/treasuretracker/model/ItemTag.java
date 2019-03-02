package com.marion.treasuretracker.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tag", schema = "public")
public class ItemTag implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Item itemId;

    @Id
    @ManyToOne
    @JoinColumn
    private Tag tagId;

    public Item getItemId() {
        return itemId;
    }

    public void setItemId(Item itemId) {
        this.itemId = itemId;
    }

    public Tag getTagId() {
        return tagId;
    }

    public void setTagId(Tag tagId) {
        this.tagId = tagId;
    }
}
