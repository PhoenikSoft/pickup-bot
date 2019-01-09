package com.phoeniksoft.pickupbot.domain.context.interceptors

import com.phoeniksoft.pickupbot.domain.context.UserContext
import com.phoeniksoft.pickupbot.domain.core.UserQuery
import com.phoeniksoft.pickupbot.domain.core.UserQueryParams
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.core.user.UserStore
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import spock.lang.Specification

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.when

class UserInfoInterceptorTest extends Specification {
    @Mock
    UserStore userStore
    @InjectMocks
    UserInfoInterceptor userInfoInterceptor

    def setup() {
        MockitoAnnotations.initMocks(this)
    }

    def "test if user param wasn't filled"() {
        given:
        def context = new UserContext()
        def query = UserQuery.builder().build()

        when:
        def acceptable = userInfoInterceptor.isAcceptable(context, query)

        then:
        !acceptable
    }

    def "test if user has already exist in storage"() {
        given:
        def context = new UserContext()
        def params = new UserQueryParams()
        params.put(UserQueryParams.USER_ID_PARAM, "test")
        def query = UserQuery.builder().specificParams(params).build()
        when(userStore.findById("test")).thenReturn Optional.of(new User("testUser"))

        when:
        userInfoInterceptor.fillContext(context, query)

        then:
        context.getUser().getId() == "testUser"
    }

    def "test if user hasn't been stored before"() {
        given:
        def context = new UserContext()
        def params = new UserQueryParams()
        params.put(UserQueryParams.USER_ID_PARAM, "test")
        def query = UserQuery.builder().specificParams(params).build()
        when(userStore.findById("test")).thenReturn Optional.empty()
        when(userStore.save(any())).thenReturn(new User("testUser"))

        when:
        userInfoInterceptor.fillContext(context, query)

        then:
        context.getUser().getId() == "testUser"
    }
}