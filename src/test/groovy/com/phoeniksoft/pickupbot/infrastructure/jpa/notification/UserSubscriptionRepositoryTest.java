package com.phoeniksoft.pickupbot.infrastructure.jpa.notification;

import com.phoeniksoft.pickupbot.common.JpaIntegrationTest;
import com.phoeniksoft.pickupbot.domain.notification.Topic;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Sql({"/db/user.sql", "/db/user_subscriptions.sql"})
public class UserSubscriptionRepositoryTest extends JpaIntegrationTest {

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Test
    public void testSaveUserSubscription() {
        UserSubscriptionDto dto = new UserSubscriptionDto();
        UserDto userDto = new UserDto();
        userDto.setId(3L);
        dto.setUser(userDto);
        dto.setTopic(Topic.PROFILE);

        UserSubscriptionDto savedDto = userSubscriptionRepository.save(dto);

        assertNotNull(savedDto.getId());
        assertEquals(3L, (long) savedDto.getUser().getId());
        assertEquals(Topic.PROFILE, savedDto.getTopic());
        assertNotNull(savedDto.getCreated());
    }

    @Test
    public void testExistsByUserAndTopic_exists() {
        UserDto userDto = new UserDto();
        userDto.setId(3L);

        boolean exists = userSubscriptionRepository.existsByUserAndTopic(userDto, Topic.MESSAGE);

        assertTrue(exists);
    }

    @Test
    public void testExistsByUserAndTopic_not_exists() {
        UserDto userDto = new UserDto();
        userDto.setId(3L);

        boolean exists = userSubscriptionRepository.existsByUserAndTopic(userDto, Topic.PROFILE);

        assertFalse(exists);
    }
}
