package com.sad.ports;

import com.sad.domain.UserAccount;

public interface IUserDataStore {
    UserAccount findByUsername(String username);
}
