package com.phoeniksoft.pickupbot.domain.context.interceptors;

import com.phoeniksoft.pickupbot.domain.context.ContextFiller;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import lombok.AllArgsConstructor;
import lombok.val;

import java.util.List;

@AllArgsConstructor
public class ContextInterceptorsFiller implements ContextFiller {

    private final List<ContextInterceptor> interceptors;

    @Override
    public UserContext fillContext(UserQuery userQuery) {
        UserContext context = new UserContext();

        for (val interceptor : interceptors) {
            interceptor.fillContext(context, userQuery);
        }

        return context;
    }
}
