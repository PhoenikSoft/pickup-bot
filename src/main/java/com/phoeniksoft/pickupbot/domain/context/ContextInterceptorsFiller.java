package com.phoeniksoft.pickupbot.domain.context;

import com.phoeniksoft.pickupbot.domain.context.interceptors.ContextInterceptor;
import com.phoeniksoft.pickupbot.domain.core.UserQuery;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
public class ContextInterceptorsFiller implements ContextFiller {

    private final List<ContextInterceptor> interceptors;

    @Override
    public UserContext fillContext(UserQuery userQuery) {
        UserContext context = new UserContext();

        for (ContextInterceptor interceptor : interceptors) {
            if (interceptor.isAcceptable(context, userQuery)) {
                interceptor.fillContext(context, userQuery);
            }
        }

        return context;
    }
}
