package com.ahmeterdogan.dto.request;

import com.ahmeterdogan.data.enums.Roles;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterRequestDto {
    @NotBlank
    @Size(min = 3, max = 20 ,message = "The username can be a minimum of 3 characters and a maximum of 20 characters.")
    private String username;
    @NotBlank
    @Size(min = 3, max = 32 ,message = "The password name can be a minimum of 4 characters and a maximum of 32 characters.")
    private String password;
    private String name;
    private String surname;
    private String companyName;
    private Roles role;
}
