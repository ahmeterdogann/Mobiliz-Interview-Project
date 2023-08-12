package com.ahmeterdogan.dto.response;

import com.ahmeterdogan.data.enums.Roles;
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
    private Roles role;
}
