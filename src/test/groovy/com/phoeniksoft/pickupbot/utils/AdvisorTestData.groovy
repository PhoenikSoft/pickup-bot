package com.phoeniksoft.pickupbot.utils

import com.phoeniksoft.pickupbot.domain.advisor.Advice
import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.user.User

trait AdvisorTestData {

    Advice validAdvice() {
        Advice.builder().id("testId").msg("testMsg").build()
    }

    UserContext validUserContext() {
        new UserContext(user: new User())
    }
}