package com.unal.lizzard.Services;

import com.unal.lizzard.Model.Role;
import com.unal.lizzard.Model.User;
import com.unal.lizzard.Repositories.RoleRepository;
import com.unal.lizzard.Repositories.UserRepository;
import com.unal.lizzard.Services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) throws Exception {
        if (passwordConfirm(user.getPassword(), user.getPasswordRepeat())) {
            user.setRoles(Arrays.asList(roleRepo.findByName("ROLE_USER")));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userRepo.save(user);
        }
        throw new Exception("Los campos de contraseña no coinciden");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuario y/o contraseña incorrecta");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    @Override
    public User getLoggedUser() {
        //obtiene el usuario loggeado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDetails loggedUser = null;
        //verifica si el objetro traido de la sesión es el usuario
        if (principal instanceof UserDetails) {
            loggedUser = (UserDetails) principal;
        }
        User user;
        try {
            user = userRepo.findByEmail(loggedUser.getUsername());
        } catch (Exception e) {
            user = null;
        }

        return user;
    }

    private boolean passwordConfirm(String password, String passwordRepeat) {
        if (password.equals(passwordRepeat)) {
            return true;
        }
        return false;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
