package com.phoeniksoft.pickupbot.domain.history;

import com.phoeniksoft.pickupbot.domain.context.UserContext;

/**
 * Service that stores proposals sent by users. It could be messages they use or just some feedback.
 */
public interface UserProposalsService {

    UserProposal saveProposal(UserContext context);
}
