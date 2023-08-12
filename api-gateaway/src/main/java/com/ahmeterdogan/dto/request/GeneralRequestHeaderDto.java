package com.ahmeterdogan.dto.request;

import com.ahmeterdogan.enums.Roles;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralRequestHeaderDto {
    private Long userId;
    private String name;
    private String surname;
    private Long companyId;
    private String companyName;
    private Roles role;
}
