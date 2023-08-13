package com.ahmeterdogan.constants;

public interface ApiUrl {
    String VERSION = "api/v1";
    String VEHICLES = VERSION + "/vehicles";
    String SAVE = "/save";
    String SAVE_ALL = "/saveAll";
    String GET_VEHICLES_BY_GROUP_ID = "/get-vehicles-by-group-id/{groupId}";
    String GET_ALL_VEHICLES_FOR_COMPANY_ADMIN = "/all-for-company-admin";
    String SEARCH_VEHICLES_BY_GROUP_NAME = "/search-vehicles-by-group-name";
    String GET_VEHICLE_BY_ID = "/{id}";
    String GET_DIRECTLY_AUTHORIZED_VEHICLE = "/get-directly-authorized-vehicle";
    String AUTHORIZED_USER_TO_VEHICLE = "/authorize-user-to-vehicle";
    String UPDATE = "/update";
    String DELETE = "/delete";
    String GET_DIRECTLY_AUTHORIZED_VEHICLE_BY_USER_ID = "/get-directly-authorized-vehicle-by-user-id/{userId}";
    String UPDATE_VEHICLE_GROUP = "/update-vehicle-group";

}
