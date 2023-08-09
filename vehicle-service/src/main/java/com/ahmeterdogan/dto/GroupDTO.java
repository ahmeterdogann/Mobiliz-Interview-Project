package com.ahmeterdogan.dto;

public class GroupDTO {
    private long id;
    private String name;
    private GroupDTO parentGroup;

    public GroupDTO(long id, String name, GroupDTO parentGroup) {
        this.id = id;
        this.name = name;
        this.parentGroup = parentGroup;
    }

    public GroupDTO() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GroupDTO getParentGroup() {
        return parentGroup;
    }

    public void setParentGroup(GroupDTO parentGroup) {
        this.parentGroup = parentGroup;
    }
}