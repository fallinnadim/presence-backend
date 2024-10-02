package com.fallinnadim.presence_app.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class Users {
    @Id
    private UUID id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String fullname;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, name = "created_by")
    private String createdBy;
    @Column(nullable = false, name = "modified_by")
    private String modifiedBy;
    @CreatedDate
    @Column(nullable = false, name = "created_at")
    private Timestamp createdAt;
    @LastModifiedDate
    @Column(nullable = false, name = "updated_at")
    private Timestamp updatedAt;

    public Users() {
    }

    public Users(UUID id, String username, String email, String fullname, String password, String createdBy, String modifiedBy, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullname = fullname;
        this.password = password;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
