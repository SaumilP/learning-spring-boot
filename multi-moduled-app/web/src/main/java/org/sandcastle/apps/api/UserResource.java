package org.sandcastle.apps.api;

import org.sandcastle.apps.dto.User;
import org.sandcastle.apps.service.UserService;
import org.sandcastle.apps.service.UserServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserResource {
    private final UserService userService;

    @Autowired
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public @ResponseBody
    User findOne(String userId) throws UserServiceException {
        return userService.find("123");
    }
}
