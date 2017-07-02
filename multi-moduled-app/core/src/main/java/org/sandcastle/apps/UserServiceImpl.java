package org.sandcastle.apps;

import org.sandcastle.apps.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User find(String id) throws UserServiceException {
        log.info("Attempting to find user for id {}", id);
        return null;
    }
}
