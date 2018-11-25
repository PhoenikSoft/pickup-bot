package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @org.hibernate.annotations.Type(type = "pg-uuid")
    @Column(name = "user_id")
    private UUID id;

    @Column(name = "telegram_id")
    private String telegramId;

    User toUser() {
        return new User(telegramId);
    }

    public static UserDto of(@NonNull User user) {
        UserDto userDto = new UserDto();
        if (user.getId() != null) {
            userDto.setTelegramId(user.getId());
        }
        return userDto;
    }

}
