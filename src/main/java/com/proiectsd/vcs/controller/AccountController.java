package com.proiectsd.vcs.controller;

import com.proiectsd.vcs.model.User;
import com.proiectsd.vcs.repository.ProjectRepository;
import com.proiectsd.vcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/user")
    public String getAccount(Model model) {
        String usernameLoggedIn = loggedInUsername();
        model.addAttribute("currentuser", usernameLoggedIn);
        return "redirect:/" + usernameLoggedIn;
    }

    @GetMapping("/{username}")
    public String userAccount(@PathVariable final String username, Model model) {
        User userRequested = userService.findByUsername(username);
        if (userRequested == null) {
            return "error";
        }
        model.addAttribute("userRequested", userRequested);
        model.addAttribute("projects", projectRepository.findByUsers_Username(username));
        return "user";
    }

    public static String loggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String usernameLoggedIn;
        if (principal instanceof UserDetails) {
            usernameLoggedIn = ((UserDetails)principal).getUsername();
        } else {
            usernameLoggedIn = principal.toString();
        }
        return usernameLoggedIn;
    }
}
