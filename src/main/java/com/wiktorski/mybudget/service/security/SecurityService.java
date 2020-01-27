package com.wiktorski.mybudget.service.security;

import com.wiktorski.mybudget.model.entity.User;

public interface SecurityService {
    String getLoggedInUsername();

    void autoLogin(String login, String password);

    User getLoggedInUser();
}
