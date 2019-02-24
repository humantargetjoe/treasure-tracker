package com.marion.treasuretracker.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "changelog", schema = "public")
public class ChangeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private
    Long id;

    @Column(name = "TIMESTAMP", nullable = false)
    private Date timestamp;

    @Column(name = "REASON", nullable = false)
    private ReasonType reason;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @ManyToOne
    private Item item;

    @ManyToOne
    private Container container;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public ReasonType getReason() {
        return reason;
    }

    public void setReason(ReasonType reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Container getContainer() {
        return container;
    }

    public void setContainer(Container container) {
        this.container = container;
    }
}
