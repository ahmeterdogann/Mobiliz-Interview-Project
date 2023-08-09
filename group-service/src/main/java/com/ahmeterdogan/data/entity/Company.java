package com.ahmeterdogan.data.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "companies"
)
public class Company {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "name"
    )
    private String name;

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Company() {
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
}