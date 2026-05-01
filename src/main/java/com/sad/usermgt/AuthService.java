package com.sad.usermgt;

import com.sad.domain.UserAccount;
import com.sad.ports.IAccountService;
import com.sad.ports.IAuthService;

public class AuthService implements IAuthService {
    private final IAccountService accountService;
    private UserAccount currentUser;

    public AuthService(IAccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public UserAccount login(String username, String password) {
        UserAccount account = accountService.findByUsername(username);
        if (!accountService.passwordMatches(account, password)) {
            throw new IllegalArgumentException("Invalid username or password.");
        }
        currentUser = account;
        System.out.println("[AuthService] Login successful for " + account.getUsername());
        return account;
    }

    @Override
    public void logout() {
        currentUser = null;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }
}
