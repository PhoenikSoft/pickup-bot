package com.phoeniksoft.pickupbot.infrastructure.jpa.user;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "telegram_id")
    private String telegramId;

    public User toUser() {
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
