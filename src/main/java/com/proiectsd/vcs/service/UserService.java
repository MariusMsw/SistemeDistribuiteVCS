package com.proiectsd.vcs.service;

import com.proiectsd.vcs.model.User;

import java.util.List;

public interface UserService {

    void save(User user);

    User findByUsername(String username);

    List<User> findAll();
}
