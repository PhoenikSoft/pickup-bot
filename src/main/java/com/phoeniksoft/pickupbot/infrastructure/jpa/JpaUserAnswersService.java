package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.history.UserAnswerHistory;
import com.phoeniksoft.pickupbot.domain.history.UserAnswersService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaUserAnswersService implements UserAnswersService {

    private final UserAnswersRepository userAnswersRepository;
    private final UserRepository userRepository;
    private final UserHistoryRepository userHistoryRepository;

    @Override
    public UserAnswerHistory saveAnswer(UserContext context) {
        UserDto userDto = userRepository.findByTelegramId(context.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such telegram id for save"));
        UserHistoryDto lastUserHistory = userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto)
                .orElseThrow(() -> new IllegalStateException("Cannot save user answer because there is no advices in user history"));
        Advice lastAdvice = Advice.builder().id(lastUserHistory.getAdviceId().toString()).build();
        return userAnswersRepository.save(UserAnswerDto.of(userDto, context.getUserAnswer(), lastAdvice)).toUserAnswerHistory();
    }
}
