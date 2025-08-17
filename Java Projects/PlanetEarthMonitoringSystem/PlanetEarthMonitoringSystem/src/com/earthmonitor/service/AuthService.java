package com.earthmonitor.service;

import com.earthmonitor.dao.UserDAO;
import com.earthmonitor.exception.MyException;
import com.earthmonitor.model.User;
import com.earthmonitor.util.PasswordUtil;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) throws MyException {
        if (username == null || username.isBlank()) throw new MyException("Username required");
        if (password == null || password.isBlank()) throw new MyException("Password required");
        User user = userDAO.findByUsername(username);
        if (user == null) throw new MyException("User not found");
        String hash = PasswordUtil.hash(username, password);
        if (!hash.equals(user.getPasswordHash())) throw new MyException("Invalid credentials");
        return user;
    }

    public void register(String username, String password, User.Role role) throws MyException {
        if (username.length() < 3) throw new MyException("Username must be at least 3 chars");
        if (password.length() < 6) throw new MyException("Password must be at least 6 chars");
        String hash = PasswordUtil.hash(username, password);
        userDAO.createUser(username, hash, role);
    }
}
