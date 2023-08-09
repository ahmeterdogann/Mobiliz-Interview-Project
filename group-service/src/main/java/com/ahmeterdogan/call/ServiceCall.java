package com.ahmeterdogan.call;

import com.ahmeterdogan.constant.ApiUrl;
import com.ahmeterdogan.dto.VehicleDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ServiceCall {
    private final RestTemplate restTemplate;

    public ServiceCall(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<VehicleDTO> getVehiclesByGroupId(long groupId) {
        String url = ApiUrl.VEHICLE_SERVICE_GROUPED + String.format("?groupId=%d", groupId);
        List<VehicleDTO> vehicles = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<VehicleDTO>>() {}
        ).getBody();

        return vehicles;
    }
}
