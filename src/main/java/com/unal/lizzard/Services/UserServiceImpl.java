package com.unal.lizzard.Services;

import com.unal.lizzard.Model.Role;
import com.unal.lizzard.Model.User;
import com.unal.lizzard.Repositories.UserRepository;
import com.unal.lizzard.Services.interfaces.UserService;
import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public User saveUser(User user) {
        user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        return userRepo.save(user);
    }
}
