package com.sad.UI.mvc;

import com.sad.domain.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class SessionModel {
    private final List<SessionObserver> observers = new ArrayList<>();
    private UserAccount currentUser;
    private String lastMessage = "Not logged in.";

    public void subscribe(SessionObserver observer) {
        observers.add(observer);
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public UserAccount getCurrentUser() {
        return currentUser;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void loginSucceeded(UserAccount account) {
        currentUser = account;
        lastMessage = "Logged in as " + account.getUsername();
        notifyObservers();
    }

    public void loginFailed(String message) {
        currentUser = null;
        lastMessage = message;
        notifyObservers();
    }

    public void logout() {
        currentUser = null;
        lastMessage = "Logged out.";
        notifyObservers();
    }

    private void notifyObservers() {
        for (SessionObserver observer : observers) {
            observer.onSessionChanged(this);
        }
    }
}
