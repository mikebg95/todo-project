package com.example.todoapp.model;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "items") // mongodb collection name
public class Item {
    @Id
    private String id;
    private String text;

    @Indexed
    private String ownerId;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Item() {}
    public Item(String text) {
        this.text = text;
    }
    public Item(String text, String ownerId) {
        this.text = text;
        this.ownerId = ownerId;
    }

    public static Item of(String text, String ownerId) {
        return new Item(text, ownerId);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
