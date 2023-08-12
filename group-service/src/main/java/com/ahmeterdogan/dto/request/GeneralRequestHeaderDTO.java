package com.ahmeterdogan.dto.request;

import com.ahmeterdogan.data.enums.Roles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeneralRequestHeaderDTO {
    private Long userId;
    private String name;
    private String surname;
    private Long companyId;
    private String companyName;
    private Roles roles;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}