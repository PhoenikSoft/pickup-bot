package com.phoeniksoft.pickupbot.utils

import com.phoeniksoft.pickupbot.domain.advisor.Advice
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.notification.Topic

import static com.phoeniksoft.pickupbot.domain.context.UserContext.ContextPayload.TOPIC_PARAM

trait AdvisorTestData {

    Advice validAdvice() {
        Advice.builder().id("testId").msg("testMsg").build()
    }

    UserContext validUserContext() {
        new UserContext(user: new User())
    }

    UserContext validUserContextWithTopic() {
        def context = validUserContext()
        context.payload[TOPIC_PARAM] = Topic.MESSAGE
        context
    }
}