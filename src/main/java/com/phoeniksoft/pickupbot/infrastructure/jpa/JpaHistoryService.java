package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import com.phoeniksoft.pickupbot.domain.history.UserHistory;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class JpaHistoryService implements HistoryService {

    private final UserHistoryRepository userHistoryRepository;
    private final UserRepository userRepository;

    @Override
    public UserHistory saveHistory(User user, Advice advice) {
        UserDto userDto = userRepository.findByTelegramId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such telegram id for save"));
        return userHistoryRepository.save(UserHistoryDto.of(userDto, advice)).toUserHistory();
    }

    @Override
    public Optional<String> getLastAdviceId(User user) {
        UserDto userDto = userRepository.findByTelegramId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such telegram id for save"));
        return userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto)
                .map(history -> history.getAdviceId().toString());
    }

    @Override
    public Set<String> getPastAdvicesIds(User user) {
        UserDto userDto = userRepository.findByTelegramId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such telegram id for save"));
        return userHistoryRepository.findDistinctAdviceIdsByUserId(userDto)
                .stream()
                .map(id -> id.toString())
                .collect(Collectors.toSet());
    }
}
