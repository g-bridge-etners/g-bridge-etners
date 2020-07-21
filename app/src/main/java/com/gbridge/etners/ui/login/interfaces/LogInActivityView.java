package com.gbridge.etners.ui.login.interfaces;

public interface LogInActivityView {

    void validateSuccess(String text);

    void validateFailure(String message);

    void logInSuccess(String message, String token, Boolean isAdmin);

}
