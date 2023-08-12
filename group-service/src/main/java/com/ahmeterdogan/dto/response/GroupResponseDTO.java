package com.ahmeterdogan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupResponseDTO {
    private Long id;
    private String name;
    private Long companyId;
    private boolean root;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupResponseDTO groupResponseDTO = (GroupResponseDTO) o;
        return Objects.equals(id, groupResponseDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
