package com.phoeniksoft.pickupbot.infrastructure.neo4j;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.advisor.AdviceStore;
import com.phoeniksoft.pickupbot.domain.advisor.NextAdviceParams;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class Neo4jAdviceStore implements AdviceStore {

    private final Neo4jAdviceRepository adviceRepository;

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
    public Advice getDefaultAdvice() {
        return adviceRepository.getDefaultNode().toAdvice();
    }
}
