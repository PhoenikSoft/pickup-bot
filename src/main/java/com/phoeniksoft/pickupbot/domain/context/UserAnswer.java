package com.phoeniksoft.pickupbot.domain.context;

import com.phoeniksoft.pickupbot.domain.common.CoreConstants;
import lombok.Getter;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Getter
public enum UserAnswer implements CoreConstants {
    YES, NO, LIKE_ADVICE(GOOD_ADVICE_LABEL), DISLIKE_ADVICE(BAD_ADVICE_LABEL);

    final String label;

    UserAnswer() {
        this.label = name();
    }

    UserAnswer(String label) {
        this.label = label;
    }

    public static UserAnswer getInstance(String label) {
        return Stream.of(values())
                .filter(answer -> answer.label.equals(label))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException());
    }
}
