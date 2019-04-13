package com.phoeniksoft.pickupbot.infrastructure.jpa.answer;

import com.phoeniksoft.pickupbot.common.JpaIntegrationTest;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@Sql({"/db/user.sql", "/db/user_proposals.sql"})
public class UserProposalsRepositoryTest extends JpaIntegrationTest {

    @Autowired
    private UserProposalsRepository userProposalsRepository;

    @Test
    public void testSaveUserProposal() {
        UserProposalDto dto = new UserProposalDto();
        UserDto userDto = new UserDto();
        userDto.setId(3L);
        dto.setUser(userDto);
        dto.setMessage("test message");

        UserProposalDto savedDto = userProposalsRepository.save(dto);

        assertNotNull(savedDto.getId());
        assertEquals(3L, (long) savedDto.getUser().getId());
        assertEquals("test message", savedDto.getMessage());
        assertNotNull(savedDto.getCreated());
    }

    @Test
    public void testCountByUser_existUser() {
        UserDto userDto = new UserDto();
        userDto.setId(3L);

        long userProposalsCount = userProposalsRepository.countByUser(userDto);

        assertEquals(2L, userProposalsCount);
    }

    @Test
    public void testCountByUser_nonExistUser() {
        UserDto userDto = new UserDto();
        userDto.setId(4L);

        long userProposalsCount = userProposalsRepository.countByUser(userDto);

        assertEquals(0L, userProposalsCount);
    }
}
