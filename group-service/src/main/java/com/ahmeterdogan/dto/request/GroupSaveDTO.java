package com.ahmeterdogan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupSaveDTO {
    private String name;
    private Long companyId;
    private boolean root;
    private Long parentId;
}