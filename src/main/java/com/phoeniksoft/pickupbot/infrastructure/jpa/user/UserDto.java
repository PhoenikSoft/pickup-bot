package com.phoeniksoft.pickupbot.infrastructure.jpa.user;

import com.phoeniksoft.pickupbot.domain.core.user.User;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Data
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "telegram_id")
    private String telegramId;

    public User toTelegramUser() {
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
