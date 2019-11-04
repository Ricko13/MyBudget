package com.wiktorski.mybudget.service.security;

import com.wiktorski.mybudget.model.User;

public interface SecurityService {
    String getLoggedInUsername();

    void autoLogin(String login, String password);

    User getLoggedInUser();
}