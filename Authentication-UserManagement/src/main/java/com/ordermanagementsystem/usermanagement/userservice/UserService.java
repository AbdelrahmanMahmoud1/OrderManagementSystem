package com.ordermanagementsystem.usermanagement.userservice;

import com.ordermanagementsystem.usermanagement.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService  {

    private final UserRepository userRepository;
    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public void saveUser(User user){userRepository.save(user);}

}
