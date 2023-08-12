package com.ahmeterdogan.dto.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveDTO {
    public String name;
    public String surname;
    public long companyId;
    private String companyName;
}