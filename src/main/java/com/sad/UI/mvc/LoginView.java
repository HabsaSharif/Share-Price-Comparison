package com.sad.UI.mvc;

public class LoginView implements SessionObserver {
    private final AuthController authController;

    public LoginView(AuthController authController, SessionModel sessionModel) {
        this.authController = authController;
        sessionModel.subscribe(this);
    }

    public void submitLogin(String username, String password) {
        authController.login(username, password);
    }

    @Override
    public void onSessionChanged(SessionModel sessionModel) {
        System.out.println(sessionModel.getLastMessage());
    }
}
