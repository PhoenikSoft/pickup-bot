package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.MessageStore;
import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewAdviceForUserException;
import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Neo4jMessageStore implements MessageStore {

    private final Neo4jMessageRepository messageRepository;
    private final HistoryService historyService;
    private final Random ran;

    @Override
    public Advice getStartMessageForUser(User user) {
        Set<String> pastAdvicesIds = historyService.getPastAdvicesIds(user);
        Set<Long> pastAdvicesIdsAsLongs = pastAdvicesIds.stream().map(Long::valueOf).collect(Collectors.toSet());
        List<MessageDto> startNodes = messageRepository.getStartNodesThatWereNotUsed(pastAdvicesIdsAsLongs);
        if (startNodes.isEmpty()) {
            throw new NoNewAdviceForUserException(user.getId());
        } else {
            return startNodes.get(ran.nextInt(startNodes.size())).toAdvice();
        }
    }
}