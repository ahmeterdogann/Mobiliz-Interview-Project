package com.ahmeterdogan.dto;

import java.util.ArrayList;
import java.util.List;

public class Node {
    private long id;
    private String name;
    private List<Node> subgroups;
    private List<VehicleDTO> vehicles;

    public Node(long id, String name) {
        this.id = id;
        this.name = name;
        this.subgroups = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Node> getSubgroups() {
        return subgroups;
    }

    public void addSubgroup(Node subgroup) {
        subgroups.add(subgroup);
    }
}
