package com.phoeniksoft.pickupbot.infrastructure.jpa.history;

import com.phoeniksoft.pickupbot.domain.advisor.Advice;
import com.phoeniksoft.pickupbot.domain.history.UserHistory;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
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
@Table(name = "user_history")
@ToString(exclude = "user")
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
                .user(user.toTelegramUser())
                .adviceId(adviceId.toString())
                .created(created)
                .build();
    }

    static UserHistoryDto of(@NonNull UserDto user, @NonNull Advice advice) {
        UserHistoryDto dto = new UserHistoryDto();
        dto.user = user;
        dto.adviceId = Long.valueOf(advice.getId());
        return dto;
    }
}
