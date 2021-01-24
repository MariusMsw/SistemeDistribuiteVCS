package com.proiectsd.vcs.controller;

import com.proiectsd.vcs.model.Project;
import com.proiectsd.vcs.model.User;
import com.proiectsd.vcs.repository.ProjectRepository;
import com.proiectsd.vcs.repository.UserRepository;
import com.proiectsd.vcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class AddProjectController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/addproject")
    public String moveToAddRepository() {
        String usernameLoggedIn = AccountController.loggedInUsername();
        return "redirect:/" + usernameLoggedIn + "/addproject";
    }

    @GetMapping("/{username}/addproject")
    public String showAddRepository(@PathVariable final String username,
                                    Model model) {
        String usernameLoggedIn = AccountController.loggedInUsername();
        model.addAttribute("username", usernameLoggedIn);
        return "addproject";
    }

    @PostMapping("/{usernameUrl}/addproject")
    public String addRepository(@PathVariable final String usernameUrl,
                                @ModelAttribute("projectForm") @Valid Project projectForm,
                                Model model,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "addproject";
        }
        Project newProject = new Project(projectForm.getTitle(), projectForm.getDescription());
        User currentUser = userService.findByUsername(usernameUrl);

        projectRepository.save(newProject);
        currentUser.addProject(newProject);
        userRepository.save(currentUser);

        return "redirect:/" + usernameUrl + "/" + newProject.getUrl();
    }
}
