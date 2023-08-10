package com.ahmeterdogan.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "vehicles")
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "plate_number", length = 20, nullable = false)
    private String plateNumber;
    @Column(name = "brand", length = 100, nullable = false)
    private String brand;
    @Column(name = "model", length = 100, nullable = false)
    private String model;
    @Column(name = "model_year", nullable = false)
    private int modelYear;
    @Column(name = "chassis_number", length = 50)
    private String chassisNumber;
    @Column(name = "label", length = 100)
    private String label;
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
}