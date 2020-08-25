package com.wiktorski.mybudget.service;

import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.service.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBasicService {

    @Autowired
    private SecurityService securityService;

    public User getCurrentUser() {
        return securityService.getLoggedInUser();
    }

}
