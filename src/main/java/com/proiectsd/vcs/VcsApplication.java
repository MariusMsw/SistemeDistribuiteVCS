package com.proiectsd.vcs;

import com.proiectsd.vcs.model.Project;
import com.proiectsd.vcs.model.User;
import com.proiectsd.vcs.repository.ProjectRepository;
import com.proiectsd.vcs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VcsApplication implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectRepository projectRepository;


    public static void main(String[] args) {
        SpringApplication.run(VcsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        User marius = new User("marius", "marius@gmail.com", "marius");
        User bogdan = new User("bogdan", "bogdan@gmail.com", "bogdan");
        Project rep1 = new Project("marius repo");

        projectRepository.save(rep1);
        marius.addProject(rep1);
        userService.save(marius);

        userService.save(bogdan);
        userService.save(new User("cosmin", "cosmin@gmail.com", "cosmin"));
        userService.save(new User("alina", "alina@gmail.com", "alina"));
        userService.save(new User("andrei", "andrei@gmail.com", "andrei"));
    }
}
