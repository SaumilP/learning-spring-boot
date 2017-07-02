package org.sandcastle.apps.internal;

import org.sandcastle.apps.dto.Gender;
import org.sandcastle.apps.dto.User;
import org.sandcastle.apps.service.UserService;
import org.sandcastle.apps.service.UserServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User find(String id) throws UserServiceException {
        log.info("Attempting to find user for id {}", id);
        return new User("saumil",
                "saumil",
                "patel",
                LocalDate.of(1992, 01, 01),
                Gender.MALE
        );
    }
}
