package com.ahmeterdogan.dto.request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTORequest {
    public Long id;
    public String name;
    public String surname;
    public long companyId;
    private String companyName;
}