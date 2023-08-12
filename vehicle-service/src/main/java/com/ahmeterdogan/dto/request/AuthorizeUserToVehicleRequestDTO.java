package com.ahmeterdogan.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeUserToVehicleRequestDTO {
    private Long userId;
    private Long vehicleId;
}
