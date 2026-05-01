package com.sad.usermgt;

import com.sad.domain.UserAccount;
import com.sad.ports.IAccountService;
import com.sad.ports.IUserDataStore;

public class AccountService implements IAccountService {
    private final IUserDataStore userDataStore;

    public AccountService(IUserDataStore userDataStore) {
        this.userDataStore = userDataStore;
    }

    @Override
    public UserAccount findByUsername(String username) {
        return userDataStore.findByUsername(username);
    }

    @Override
    public boolean passwordMatches(UserAccount account, String password) {
        return account != null && account.getPassword().equals(password);
    }
}
