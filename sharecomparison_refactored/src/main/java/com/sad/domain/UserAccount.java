package com.sad.domain;


//domain lvl obj of user account, for now verrrryyy simple
public class UserAccount {
    private final String username;
    private final String password;
    private final String displayName;

    public UserAccount(String username, String password, String displayName) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username must not be empty.");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password must not be empty.");
        }
        if (displayName == null || displayName.isBlank()) {
            throw new IllegalArgumentException("Display name must not be empty.");
        }

        this.username = username.trim().toLowerCase();
        this.password = password;
        this.displayName = displayName.trim();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDisplayName() {
        return displayName;
    }
}