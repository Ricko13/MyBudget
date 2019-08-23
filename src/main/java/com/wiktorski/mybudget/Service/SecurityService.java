package com.wiktorski.mybudget.Service;

import com.wiktorski.mybudget.Model.User;

public interface SecurityService {
    String findLoggedInUsername();

    void autoLogin(String login, String password);

    User getLoggedInUser();
}
