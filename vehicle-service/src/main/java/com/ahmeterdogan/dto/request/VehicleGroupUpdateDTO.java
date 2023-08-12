package com.ahmeterdogan.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleGroupUpdateDTO {
    private long id;
    private Long groupId;
}
