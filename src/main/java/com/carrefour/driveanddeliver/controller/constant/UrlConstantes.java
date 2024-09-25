package com.carrefour.driveanddeliver.controller.constant;

public class UrlConstantes {

    public static final String TIMESLOTS_URL = "/time-slots";
    public static final String AVAILABLE_TIMESLOTS_URL = "/available";
    public static final String DELIVERIES_URL = "/deliveries";
    public static final String USERS_URL = "/users";
    public static final String USER_REGISTER_URL = "/register";
    public static final String DELIVERY_METHODS_URL = "/methods";

    
    public static final String AUTH_URL = "/auth";
    public static final String LOGIN_URL = "/login";

    private UrlConstantes() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
