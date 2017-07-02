package org.sandcastle.apps;

import org.sandcastle.apps.dto.User;

public interface UserService {
    User find(final String id) throws UserServiceException;
}
