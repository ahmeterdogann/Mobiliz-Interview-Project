package com.ahmeterdogan.constants;

public interface ApiUrl {
    String VERSION = "api/v1";
    String GROUPS = VERSION + "/groups";
    String SAVE = "/save";
    String USER_VEHICLES_TREE = "/user-vehicles-tree";
    String USER_VEHICLES_LIST = "/user-vehicles-list";
    String USER_VEHICLES_LIST_BY_USER_ID = "/user-vehicles-list-by-user-id";
    String USER_GROUPS_LIST = "/user-groups-list";
    String FIND_BY_ID = "/{id}";
    String AUTHORIZE_USER_TO_GROUP = "/authorize-user-to-group";
    String DELETE = "{id}/delete";
}
