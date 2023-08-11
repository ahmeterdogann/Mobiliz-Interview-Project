package com.ahmeterdogan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequestDto {
    public String name;
    public String surname;
    public long companyId;
    private String companyName;
}