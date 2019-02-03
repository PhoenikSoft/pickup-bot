package com.phoeniksoft.pickupbot.infrastructure.jpa.history;

import com.phoeniksoft.pickupbot.common.JpaIntegrationTest;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Sql({"/db/user.sql", "/db/user_history.sql"})
public class UserHistoryRepositoryTest extends JpaIntegrationTest {

    @Autowired
    private UserHistoryRepository userHistoryRepository;

    @Test
    public void findFirstByUserOrderByCreatedDesc_exist(){
        UserDto userDto = new UserDto();
        userDto.setId(2L);

        UserHistoryDto lastHistory = userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto).get();

        assertEquals(6, (long)lastHistory.getId());
        assertEquals(2L, (long)lastHistory.getUser().getId());
        assertEquals(4L, (long)lastHistory.getAdviceId());
        assertEquals(LocalDateTime.of(2019, 2, 2, 20, 0, 6), lastHistory.getCreated());
    }

    @Test
    public void findFirstByUserOrderByCreatedDesc_non_exist(){
        UserDto userDto = new UserDto();
        userDto.setId(-1L);

        Optional<UserHistoryDto> lastHistoryOpt = userHistoryRepository.findFirstByUserOrderByCreatedDesc(userDto);

        assertFalse(lastHistoryOpt.isPresent());
    }

    @Test
    public void findDistinctAdviceIdsByUserId_exist(){
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        Set<Long> advices = userHistoryRepository.findDistinctAdviceIdsByUserId(userDto);

        assertEquals(2, advices.size());
    }

    @Test
    public void findDistinctAdviceIdsByUserId_empty(){
        UserDto userDto = new UserDto();
        userDto.setId(-1L);

        Set<Long> advices = userHistoryRepository.findDistinctAdviceIdsByUserId(userDto);

        assertEquals(0, advices.size());
    }
}
