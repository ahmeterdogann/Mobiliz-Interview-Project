package com.ahmeterdogan.dto.response;

import com.ahmeterdogan.data.enums.Roles;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserAuthDTO {
    private Long id;
    private String name;
    private String surname;
    private Long companyId;
    private String companyName;
    private Roles roles;
}
