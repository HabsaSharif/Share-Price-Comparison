package com.sad.ports;

import com.sad.domain.UserAccount;

public interface IAuthService {
    UserAccount login(String username, String password);
    void logout();
}
