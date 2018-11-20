package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.UserHistory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaHistoryService implements HistoryService {

    private final UserHistoryRepository userHistoryRepository;

    @Override
    public UserHistory saveHistory(User user, Advice advice) {
        return userHistoryRepository.save(UserHistoryDto.of(user, advice)).toUserHistory();
    }
}
