package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.core.user.User;
import com.phoeniksoft.pickupbot.domain.history.UserHistory;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_history")
public class UserHistoryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDto user;

    @Column(name = "advice_id", nullable = false)
    private Long adviceId;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    UserHistory toUserHistory() {
        return UserHistory.builder()
                .id(id)
                .user(user.toUser())
                .adviceId(adviceId.toString())
                .created(created)
                .build();
    }

    static UserHistoryDto of(@NonNull User user, @NonNull Advice advice) {
        UserHistoryDto dto = new UserHistoryDto();
        dto.user = UserDto.of(user);
        dto.adviceId = Long.valueOf(advice.getId());
        return dto;
    }
}
