package com.phoeniksoft.pickupbot.domain.core;

import java.util.HashMap;
import java.util.Map;

public class UserQueryParams extends HashMap<String, Object> {

    public static String USER_ID_PARAM = "userId";
    public static String ADVICE_ID_PARAM = "adviceId";

    public UserQueryParams() {
    }

    public UserQueryParams(Map<? extends String, ?> m) {
        super(m);
    }
}
