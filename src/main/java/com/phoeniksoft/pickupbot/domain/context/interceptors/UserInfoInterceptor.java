package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams;
import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.core.user.UserStore;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class UserInfoInterceptor implements ContextInterceptor {

    private final UserStore userStore;

    @Override
    public void fillContext(UserContext context, UserQuery userQuery) {
        UserQueryParams params = userQuery.getSpecificParams();
        if (params.containsKey(UserQueryParams.USER_ID_PARAM)) {
            Optional<User> userOpt = userStore.findById(params.get(UserQueryParams.USER_ID_PARAM).toString());
            userOpt.ifPresent(context::setUser);
        }
    }
}
