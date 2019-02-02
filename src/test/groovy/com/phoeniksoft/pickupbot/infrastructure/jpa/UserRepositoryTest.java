package com.phoeniksoft.pickupbot.infrastructure.jpa;

import com.phoeniksoft.pickupbot.common.JpaIntegrationTest;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Sql("/db/user.sql")
public class UserRepositoryTest extends JpaIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void findByTelegramIdTest_exist() {
        String existId = "1";
        UserDto userDto = userRepository.findByTelegramId(existId).get();

        assertEquals(existId, userDto.getTelegramId());
        assertEquals(1, (long) userDto.getId());
    }

    @Test
    public void findByTelegramIdTest_not_found() {
        String nonExistId = "0";
        Optional<UserDto> userDto = userRepository.findByTelegramId(nonExistId);

        assertFalse(userDto.isPresent());
    }
}
