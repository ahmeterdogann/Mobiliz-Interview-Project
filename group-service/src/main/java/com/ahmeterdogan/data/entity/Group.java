package com.ahmeterdogan.data.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "groups"
)
public class Group {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @Column(
            name = "name",
            length = 100,
            nullable = false
    )
    private String name;
    @ManyToOne
    @JoinColumn(
            name = "company_id",
            nullable = false
    )
    private Company company;
    @Column(
            name = "root",
            nullable = false
    )
    private boolean isRoot;

    public Group(Long id, String name, Company company, boolean isRoot) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.isRoot = isRoot;
    }

    public Group() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public boolean isRoot() {
        return this.isRoot;
    }

    public void setRoot(boolean root) {
        this.isRoot = root;
    }
}