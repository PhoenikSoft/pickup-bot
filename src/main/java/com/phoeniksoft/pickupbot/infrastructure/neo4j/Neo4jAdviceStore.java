package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.AdviceType;
import com.phoeniksoft.pickupbot.domain.advisor.NextAdviceParams;
import com.phoeniksoft.pickupbot.domain.advisor.exception.NoNewAdviceForUserException;
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
    public Advice getAdviceByTypeForUser(AdviceType type, User user) {
        Set<String> pastAdvicesIds = historyService.getPastAdvicesIds(user);
        Set<Long> pastAdvicesIdsAsLongs = pastAdvicesIds.stream().map(Long::valueOf).collect(Collectors.toSet());

        String tag = type.name().toLowerCase();
        List<AdviceDto> newNodes = adviceRepository.getNodesByTagThatWereNotUsed(tag, pastAdvicesIdsAsLongs);
        if (newNodes.isEmpty()) {
            List<AdviceDto> allAdvicesByTag = adviceRepository.getNodesByTag(tag);
            Advice oldRandomAdvice = allAdvicesByTag.get(ran.nextInt(allAdvicesByTag.size())).toAdvice();
            throw new NoNewAdviceForUserException(user.getId(), oldRandomAdvice);
        } else {
            return newNodes.get(ran.nextInt(newNodes.size())).toAdvice();
        }
    }

    @Override
    public Optional<Advice> getNextAdvice(String prevAdviceId, NextAdviceParams params) {
        Object userAnswer = params.get(NextAdviceParams.USER_ANSWER_PARAM);
        return adviceRepository.getNextAdviceByUserAnswer(Long.valueOf(prevAdviceId), userAnswer.toString())
                .map(AdviceDto::toAdvice);
    }

    @Override
    public Advice getDefaultAdvice() {
        return adviceRepository.getDefaultNode().toAdvice();
    }

    @Override
    public Long getNodesCount() {
        return adviceRepository.getNodesCount();
    }
}
