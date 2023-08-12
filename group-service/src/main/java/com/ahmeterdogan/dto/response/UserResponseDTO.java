package com.ahmeterdogan.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDTO {
    public Long id;
    public String name;
    public String surname;
    public Long companyId;
    private String companyName;
}
