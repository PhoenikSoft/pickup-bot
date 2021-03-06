package com.phoeniksoft.pickupbot.infrastructure.jpa.answer;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.history.UserAnswerHistory;
import com.phoeniksoft.pickupbot.domain.history.UserAnswersService;
import com.phoeniksoft.pickupbot.infrastructure.jpa.history.UserHistoryDto;
import com.phoeniksoft.pickupbot.infrastructure.jpa.history.UserHistoryRepository;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository;
import lombok.AllArgsConstructor;

import static com.phoeniksoft.pickupbot.domain.context.UserContext.ContextPayload.PREV_ADVICE_PARAM;

@AllArgsConstructor
public class JpaUserAnswersService implements UserAnswersService {

    private final UserAnswersRepository userAnswersRepository;
    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;

    @Override
    public UserAnswerHistory saveAnswer(UserContext context) {
        UserDto userDto = userRepository.findByTelegramId(context.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such telegram id for save"));
        String lastAdviceId;
        if (context.getPayload().containsKey(PREV_ADVICE_PARAM)) {
            lastAdviceId = context.getPayload().get(PREV_ADVICE_PARAM).toString();
        } else {
            UserHistoryDto lastUserHistory = userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto)
                    .orElseThrow(() -> new IllegalStateException("Cannot save user answer because there is no advices in user history"));
            lastAdviceId = lastUserHistory.getAdviceId().toString();
        }
        Advice lastAdvice = Advice.builder().id(lastAdviceId).build();
        return userAnswersRepository.save(UserAnswerDto.of(userDto, context.getUserAnswer(), lastAdvice)).toUserAnswerHistory();
    }
}
