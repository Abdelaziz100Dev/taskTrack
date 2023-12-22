package com.tasktrak.services.interfaces;


import com.tasktrak.entities.User;

import java.util.Optional;

public interface UserService {
    User getUserById(Long id);
    Optional<User> findUserById(Long id);
    User addUser(User user);
}
