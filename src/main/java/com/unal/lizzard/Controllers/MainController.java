package com.unal.lizzard.Controllers;

import com.unal.lizzard.Model.User;
import com.unal.lizzard.Services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainController {

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void addLoggedUserToView(Model model) {
        model.addAttribute("loggedUser", userService.getLoggedUser());
    }

    @GetMapping("/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

}
