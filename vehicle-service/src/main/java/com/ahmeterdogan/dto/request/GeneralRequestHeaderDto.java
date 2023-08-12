package com.ahmeterdogan.dto.request;

import com.ahmeterdogan.data.enums.Roles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GeneralRequestHeaderDto {
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
            System.out.println("********************************************************************************GeneralRequestHeaderDto.toString() is called");
            System.out.println("UserID : " + this.userId);
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}