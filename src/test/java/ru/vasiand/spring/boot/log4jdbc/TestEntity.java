package ru.vasiand.spring.boot.log4jdbc;

import jakarta.persistence.*;

@Entity
@Table(name = "test_entity")
public class TestEntity {

    public TestEntity() {
    }

    public TestEntity(String id) {
        this.id = id;
    }

    @Id
    @Column(name = "id", nullable = false)
    private String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}