package com.ahmeterdogan.data.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "user_group_authorizations"
)
public class UserGroupAuthorization {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    Long id;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private User user;
    @ManyToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(
            name = "group_id",
            referencedColumnName = "id"
    )
    private Group group;

    public UserGroupAuthorization(Long id, User user, Group group) {
        this.id = id;
        this.user = user;
        this.group = group;
    }

    public UserGroupAuthorization() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}
