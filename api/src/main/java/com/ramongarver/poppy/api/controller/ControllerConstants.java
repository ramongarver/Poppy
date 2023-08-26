package com.ramongarver.poppy.api.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerConstants {

    // RESOURCES
    public static final String ACTIVITIES_RESOURCE = "/activities";
    public static final String ACTIVITY_PACKAGES_RESOURCE = "/activity-packages";
    public static final String USERS_RESOURCE = "/users";
    public static final String VOLUNTEERS_RESOURCE = "/volunteers";
    public static final String VOLUNTEERS_RESOURCE_PASSWORD = "/password";
    public static final String VOLUNTEER_AVAILABILITIES_RESOURCE = "/volunteer-availabilities";
    public static final String WORKGROUPS_RESOURCE = "/workgroups";


    public static final String API_ROUTE = "/api";

    public static final String ROOT_ROUTE = API_ROUTE + "/";

    public static final String AUTH_ROUTE = API_ROUTE + "/account";
    public static final String AUTH_REGISTER_ROUTE = "/register";
    public static final String AUTH_AUTHENTICATE_ROUTE = "/login";
    public static final String AUTH_MY_ACCOUNT_ROUTE = "/my-account";

    public static final String ACTIVITIES_ROUTE = API_ROUTE + ACTIVITIES_RESOURCE;

    public static final String ACTIVITY_PACKAGES_ROUTE = API_ROUTE + ACTIVITY_PACKAGES_RESOURCE;

    public static final String USERS_ROUTE = API_ROUTE + USERS_RESOURCE;

    public static final String VOLUNTEERS_ROUTE = API_ROUTE + VOLUNTEERS_RESOURCE;

    public static final String WORKGROUPS_ROUTE = API_ROUTE + WORKGROUPS_RESOURCE;

}
