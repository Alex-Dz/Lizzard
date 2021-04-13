package com.unal.lizzard.Services.interfaces;

import com.unal.lizzard.Model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User saveUser(User user) throws Exception;
    User getLoggedUser();
}
