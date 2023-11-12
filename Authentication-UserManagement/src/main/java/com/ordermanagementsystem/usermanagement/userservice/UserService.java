package com.ordermanagementsystem.usermanagement.userservice;

import com.ordermanagementsystem.usermanagement.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
//@RequiredArgsConstructor(onConstructor = @__({@Autowired,@Lazy}))
public class UserService  {

    private final UserRepository userRepository;

    // TODO: 11/12/2023 use @requiredargsconstructor no need to autowire it with it I`ll leave example do exactly what you are doing here but no need for it only @requiredargsconstructor is enough
    @Autowired
    UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    public void saveUser(User user){userRepository.save(user);}

}
