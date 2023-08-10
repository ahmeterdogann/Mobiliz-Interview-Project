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
@Table(name = "groups_to_groups")
public class GroupToGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Group parentGroup;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_id")
    private Group childGroup;
}