package com.phoeniksoft.pickupbot.infrastructure.jpa.answer;

import com.phoeniksoft.pickupbot.app.config.CoreProperties;
import com.phoeniksoft.pickupbot.domain.context.UserContext;
import com.phoeniksoft.pickupbot.domain.history.TooManyUserProposalsException;
import com.phoeniksoft.pickupbot.domain.history.UserProposal;
import com.phoeniksoft.pickupbot.domain.history.UserProposalsService;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserDto;
import com.phoeniksoft.pickupbot.infrastructure.jpa.user.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JpaUserProposalsService implements UserProposalsService {

    private final UserProposalsRepository userProposalsRepository;
    private final UserRepository userRepository;
    private final CoreProperties coreProperties;

    @Override
    public UserProposal saveProposal(UserContext context) {
        UserDto userDto = userRepository.findByTelegramId(context.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("There is no user with such telegram id for save"));
        if (userProposalsRepository.countByUser(userDto) >= coreProperties.getProposalsLimit()) {
            throw new TooManyUserProposalsException(coreProperties.getProposalsLimit());
        }
        return userProposalsRepository.save(UserProposalDto.of(userDto, context.getUserFreeText())).toUserProposal();
    }
}
