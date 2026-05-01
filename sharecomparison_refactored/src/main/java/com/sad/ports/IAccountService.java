package com.sad.ports;

import com.sad.domain.UserAccount;

public interface IAccountService {
    UserAccount findByUsername(String username);
    boolean passwordMatches(UserAccount account, String password);
}
