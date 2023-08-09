package com.ahmeterdogan.data.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "user_vehicle_authorizations"
)
public class UserVehicleAuthorization {
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
            name = "vehicle_id",
            referencedColumnName = "id"
    )
    private Vehicle vehicle;

    public UserVehicleAuthorization(Long id, User user, Vehicle vehicle) {
        this.id = id;
        this.user = user;
        this.vehicle = vehicle;
    }

    public UserVehicleAuthorization() {
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

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = this.vehicle;
    }
}
