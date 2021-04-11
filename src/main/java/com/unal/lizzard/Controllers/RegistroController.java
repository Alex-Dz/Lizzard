package com.unal.lizzard.Controllers;

import com.unal.lizzard.Model.User;
import com.unal.lizzard.Services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistroController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String registro(Model model) {
        model.addAttribute("user", new User());
        return "registerForm";
    }

        @PostMapping("/register")
    public String createUser(@ModelAttribute("user") User user, BindingResult result, ModelMap model) {
        if ((result.hasErrors())) {
            model.addAttribute("user", user);
            model.addAttribute("success", false);
        } else {
            try {
                System.out.println(userService.saveUser(user).toString());
                model.addAttribute("user", user);
                model.addAttribute("success", true);
                return "registerForm";
            } catch (Exception e) {
                return "registerForm";
            }
        }
        return "redirect:/registerForm?success=false";
    }
}
