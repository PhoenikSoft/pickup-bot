package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.NextAdviceParams;
import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewStartAdviceForUserException;
import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.history.HistoryService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
public class Neo4jAdviceStore implements AdviceStore {

    private final Neo4jAdviceRepository adviceRepository;
    private final HistoryService historyService;
    private final Random ran;

    @Override
    public Optional<Advice> getById(String id) {
        return adviceRepository.findById(Long.valueOf(id)).map(AdviceDto::toAdvice);
    }

    @Override
    public Optional<Advice> getNextAdvice(String prevAdviceId, NextAdviceParams params) {
        Object userAnswer = params.get(NextAdviceParams.USER_ANSWER_PARAM);
        return adviceRepository.getNextAdviceByUserAnswer(Long.valueOf(prevAdviceId), userAnswer.toString())
                .map(AdviceDto::toAdvice);
    }

    @Override
    public Optional<Advice> getStartAdvice() {
        List<AdviceDto> allStartNodes = adviceRepository.getAllStartNodes();
        return allStartNodes.isEmpty()
                ? Optional.empty()
                : Optional.of(allStartNodes.get(ran.nextInt(allStartNodes.size())).toAdvice());
    }

    @Override
    public Advice getStartAdviceForUser(String userId) {
        Set<String> pastAdvicesIds = historyService.getPastAdvicesIds(new User(userId));
        Set<Long> pastAdvicesIdsAsLongs = pastAdvicesIds.stream().map(Long::valueOf).collect(Collectors.toSet());
        List<AdviceDto> startNodes = adviceRepository.getStartNodesThatWerentUsed(pastAdvicesIdsAsLongs);
        if (startNodes.isEmpty()) {
            throw new NoNewStartAdviceForUserException(userId);
        } else {
            return startNodes.get(ran.nextInt(startNodes.size())).toAdvice();
        }
    }

    @Override
    public Advice getDefaultAdvice() {
        return adviceRepository.getDefaultNode().toAdvice();
    }
}
