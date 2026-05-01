package com.sad.UI.mvc;

import com.sad.domain.UserAccount;
import com.sad.ports.IUserMgt;

public class AuthController {
    private final IUserMgt userMgt;
    private final SessionModel sessionModel;

    public AuthController(IUserMgt userMgt, SessionModel sessionModel) {
        this.userMgt = userMgt;
        this.sessionModel = sessionModel;
    }

    public void login(String username, String password) {
        try {
            UserAccount account = userMgt.login(username, password);
            sessionModel.loginSucceeded(account);
        } catch (RuntimeException ex) {
            sessionModel.loginFailed(ex.getMessage());
        }
    }

    public void logout() {
        userMgt.logout();
        sessionModel.logout();
    }
}
