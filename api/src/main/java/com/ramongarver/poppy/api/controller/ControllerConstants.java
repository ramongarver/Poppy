package com.ramongarver.poppy.api.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ControllerConstants {

    public static final String API_ROUTE = "/api";

    public static final String ROOT_ROUTE = API_ROUTE + "/";

    public static final String AUTH_ROUTE = API_ROUTE + "/account";
    public static final String AUTH_REGISTER_ROUTE = "/register";
    public static final String AUTH_AUTHENTICATE_ROUTE = "/login";
    public static final String AUTH_MY_ACCOUNT_ROUTE = "/my-account";

    public static final String ACTIVITY_ROUTE = API_ROUTE + "/activities";

    public static final String USERS_ROUTE = API_ROUTE + "/users";

}
