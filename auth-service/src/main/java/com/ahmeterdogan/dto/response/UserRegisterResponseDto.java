package com.ahmeterdogan.dto.response;

import com.ahmeterdogan.data.enums.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponseDto {
    private String username;
    private Long userId;
    private String name;
    private String surname;
    private Long companyId;
    private String companyName;
    private Roles roles;
}
