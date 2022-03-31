package com.stockproject.service;

import com.stockproject.repository.UserRepository;
import com.stockproject.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService  {

    private final UserRepository registrationRepository;

    @Autowired
    public RegistrationService(UserRepository registrationRepository) {
        this.registrationRepository = registrationRepository;
    }

    public void save(User user){
        registrationRepository.save(user);
    }

    public User findUser(String username){
        return registrationRepository.findByUsername(username);
    }
}
