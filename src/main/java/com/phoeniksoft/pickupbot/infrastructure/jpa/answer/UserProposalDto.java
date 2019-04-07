package com.phoeniksoft.pickupbot.infrastructure.jpa.answer;

import com.phoeniksoft.pickupbot.domain.history.UserProposal;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user_proposals")
@ToString(exclude = "user")
public class UserProposalDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proposal_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserDto user;

    @Column(name = "message", nullable = false)
    private String message;

    @Column
    @CreationTimestamp
    private LocalDateTime created;

    UserProposal toUserProposal() {
        return UserProposal.builder()
                .id(id)
                .user(user.toTelegramUser())
                .message(message)
                .created(created)
                .build();
    }

    static UserProposalDto of(@NonNull UserDto user, @NonNull String message) {
        UserProposalDto dto = new UserProposalDto();
        dto.user = user;
        dto.message = message;
        return dto;
    }
}
