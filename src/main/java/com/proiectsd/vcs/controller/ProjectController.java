package com.proiectsd.vcs.controller;

import com.proiectsd.vcs.model.*;
import com.proiectsd.vcs.repository.ProjectRepository;
import com.proiectsd.vcs.repository.UserRepository;
import com.proiectsd.vcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;

@Controller
public class ProjectController {
    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BranchController branchController;


    @GetMapping("/{username}/{projectUrl}")
    public String userRepository(@PathVariable final String username,
                                 @PathVariable final String projectUrl,
                                 Model model) {
        User userRequested = userService.findByUsername(username);
        Project projectRequested = projectRepository.findByUrl(projectUrl);
        if (!doesRepositoryExist(username, projectUrl) || !userOwnsRepository(userRequested, projectRequested)) {
            return "error";
        }
        Set<Branch> branches = projectRequested.getBranches();
        for (Branch branch : branches) {
            if (branch.getName().equals("master")) {
                int commitsNumber = branch.getCommits().size();
                model.addAttribute("commitsNumber", commitsNumber);
            }
        }
        String usernameLoggedIn = AccountController.loggedInUsername();
        model.addAttribute("userRequested", userRequested);
        model.addAttribute("usernameLoggedIn", usernameLoggedIn);
        model.addAttribute("project", projectRequested);
        model.addAttribute("branches", branches);
        model.addAttribute("currentBranch", "master");

        // Retrieve files from db
        Set<DBFile> files = branchController.getMasterBranch(username, projectUrl).getFiles();
        model.addAttribute("files", files);

        model.addAttribute("newBranchHasChanges", false);

        return "project";
    }

    @GetMapping("/{username}/{projectUrl}/settings")
    public String repositorySettings(@PathVariable final String username,
                                     @PathVariable final String projectUrl,
                                     Model model) {
        if (!doesRepositoryExist(username, projectUrl) || !username.equals(AccountController.loggedInUsername())) {
            return "error";
        }
        User userOwner = userService.findByUsername(username);
        List<User> usersOwners = userRepository.findByProjects_Url(projectUrl);
        Project projectRequested = projectRepository.findByUrl(projectUrl);
        Set<Branch> branches = projectRequested.getBranches();
        model.addAttribute("usersOwners", usersOwners);
        model.addAttribute("userOwner", userOwner);
        model.addAttribute("project", projectRequested);
        model.addAttribute("branches", branches);
        return "projectsettings";
    }

    protected boolean doesRepositoryExist(String username, String repositoryUrl) {
        User userRequested = userService.findByUsername(username);
        Project projectRequested = projectRepository.findByUrl(repositoryUrl);
        if (userRequested == null || projectRequested == null) {
            return false;
        }
        return true;
    }

    protected boolean userOwnsRepository(User user, Project project) {
        if (user.getProjects().contains(project))
            return true;
        return false;
    }

    protected boolean customBranchDiffFromMaster(Branch customBranch, Branch masterBranch) {
        Set<Commit> cbCommits = customBranch.getCommits();
        Set<Commit> mbCommits = masterBranch.getCommits();
        if (cbCommits == null && mbCommits == null) {
            return false;
        }
        if (cbCommits.size() != mbCommits.size()) {
            return true;
        }
        return !mbCommits.containsAll(cbCommits);
    }
}