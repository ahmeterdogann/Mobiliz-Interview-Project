package com.ahmeterdogan.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
        name = "groups_to_groups"
)
public class GroupToGroup {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "parent_id"
    )
    private Group parentGroup;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "child_id"
    )
    private Group childGroup;

    public GroupToGroup(Long id, Group parentGroup, Group childGroup) {
        this.id = id;
        this.parentGroup = parentGroup;
        this.childGroup = childGroup;
    }

    public GroupToGroup() {
    }

    public Group getParentGroup() {
        return this.parentGroup;
    }

    public void setParentGroup(Group parentGroup) {
        this.parentGroup = parentGroup;
    }

    public Group getChildGroup() {
        return this.childGroup;
    }

    public void setChildGroup(Group childGroup) {
        this.childGroup = childGroup;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }
}