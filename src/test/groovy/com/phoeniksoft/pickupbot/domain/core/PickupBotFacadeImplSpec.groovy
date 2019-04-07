package com.phoeniksoft.pickupbot.domain.core

import com.phoeniksoft.pickupbot.domain.advisor.Advisor
import com.phoeniksoft.pickupbot.domain.context.ContextFiller
import com.phoeniksoft.pickupbot.domain.core.user.User
import com.phoeniksoft.pickupbot.domain.history.HistoryService
import com.phoeniksoft.pickupbot.domain.history.UserAnswersService
import com.phoeniksoft.pickupbot.domain.history.UserProposalsService
import com.phoeniksoft.pickupbot.domain.notification.SubscriptionService
import com.phoeniksoft.pickupbot.domain.notification.Topic
import com.phoeniksoft.pickupbot.utils.AdvisorTestData
import spock.lang.Specification

class PickupBotFacadeImplSpec extends Specification implements AdvisorTestData {
    def contextFiller = Mock(ContextFiller)
    def advisor = Mock(Advisor)
    def historyService = Mock(HistoryService)
    def userAnswersService = Mock(UserAnswersService)
    def subscriptionService = Mock(SubscriptionService)
    def userProposalsService = Mock(UserProposalsService)
    def pickupBotFacadeImpl = new PickupBotFacadeImpl(contextFiller, advisor, historyService, userAnswersService,
            subscriptionService, userProposalsService)

    def "test get advice - full road"() {
        given:
        def context = validUserContext()
        contextFiller.fillContext(_) >> context
        def expectedAdvice = validAdvice()
        advisor.getAdvice(context) >> expectedAdvice
        historyService.saveHistory(_ as User, expectedAdvice) >> null

        when:
        UserAdvice result = pickupBotFacadeImpl.getAdvice(new UserQuery(UserCommand.GET_START_ADVICE,
                new UserMessage("value"),
                new UserQueryParams()))

        then:
        result.msg == "testMsg"
        result.payload == [adviceId: "testId"]
    }

    def "test save user answer"() {
        given:
        def context = validUserContext()
        contextFiller.fillContext(_) >> context

        when:
        pickupBotFacadeImpl.saveUserAnswer(UserQuery.builder().build())

        then:
        1 * userAnswersService.saveAnswer(context)
    }

    def "test save user proposal"() {
        given:
        def context = validUserContext()
        contextFiller.fillContext(_) >> context

        when:
        pickupBotFacadeImpl.saveUserProposal(UserQuery.builder().build())

        then:
        1 * userProposalsService.saveProposal(context)
    }

    def "test subscribe user"() {
        given:
        def context = validUserContextWithTopic()
        contextFiller.fillContext(_) >> context

        when:
        pickupBotFacadeImpl.subscribe(UserQuery.builder().build())

        then:
        1 * subscriptionService.subscribe(context.user, Topic.MESSAGE)
    }
}