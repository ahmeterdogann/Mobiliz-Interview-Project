package com.ahmeterdogan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTreeDto {
    private Long id;
    private String name;
    private Set<GroupTreeDto> children;

    public GroupTreeDto(Long id, String name) {
        this.id = id;
        this.name = name;
        this.children = new HashSet<>();
    }

    public void addChild(GroupTreeDto groupTreeDto) {
        children.add(groupTreeDto);
    }

}
