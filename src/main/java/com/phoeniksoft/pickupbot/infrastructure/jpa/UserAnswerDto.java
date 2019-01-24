package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.context.UserAnswer;
import com.phoeniksoft.pickupbot.domain.core.UserMessage;
import com.phoeniksoft.pickupbot.domain.history.UserAnswerHistory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_answers")
@ToString(exclude = "user")
public class UserAnswerDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDto user;

    @Column(name = "answer", nullable = false)
    private String answer;

    @Column(name = "advice_id", nullable = false)
    private Long adviceId;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    UserAnswerHistory toUserAnswerHistory() {
        return UserAnswerHistory.builder()
                .id(id)
                .user(user.toUser())
                .answer(answer)
                .adviceId(adviceId.toString())
                .created(created)
                .build();
    }

    static UserAnswerDto of(@NonNull UserDto user, @NonNull UserAnswer answer, @NonNull Advice advice) {
        UserAnswerDto dto = new UserAnswerDto();
        dto.user = user;
        dto.answer = answer.name();
        dto.adviceId = Long.valueOf(advice.getId());
        return dto;
    }
}
