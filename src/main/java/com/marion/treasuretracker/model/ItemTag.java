package com.marion.treasuretracker.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "itemtag", schema = "public")
@IdClass(ItemTag.ItemTagId.class)
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

    public static class ItemTagId implements Serializable {
        private Item itemId;
        private Tag tagId;

        public ItemTagId(){}
        public ItemTagId(Item itemId, Tag tagId) {
            this.itemId = itemId;
            this.tagId = tagId;
        }

        @Override
        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ItemTag)) {
                return false;
            }
            ItemTag itemTag = (ItemTag) o;
            return Objects.equals(itemId, itemTag.itemId) && Objects.equals(tagId, itemTag.tagId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(itemId, tagId);
        }

    }
}
