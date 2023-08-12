package com.ahmeterdogan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private String name;
    private Long companyId;
    private boolean root;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupDTO groupDto = (GroupDTO) o;
        return Objects.equals(id, groupDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}