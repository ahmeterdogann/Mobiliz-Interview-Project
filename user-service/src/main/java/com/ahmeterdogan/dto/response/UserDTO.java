package com.ahmeterdogan.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    public Long id;
    public String name;
    public String surname;
    public Long companyId;
    private String companyName;
}