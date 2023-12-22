package com.tasktrak.services;

import com.tasktrak.entities.User;
import com.tasktrak.repositories.UserRepository;
import com.tasktrak.services.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserServiceImp implements UserService {
    UserRepository userRepository;
    public  UserServiceImp(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    public User getUserById(Long id){
        return userRepository.findUserById(id);
    }

    public Optional<User> findUserById(Long id) {
       return userRepository.findById(id);
    }


    public User addUser(User user) {
        return userRepository.save(user);
    }
}
