package com.wiktorski.mybudget.service.security;

import com.wiktorski.mybudget.model.User;
import com.wiktorski.mybudget.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public String getLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }

        //return userDetails.getClass().toString();
        return null;
    }

    @Autowired
    UserRepository userRepository;

    public User getLoggedInUser() {
        String username = getLoggedInUsername();
        if (username != null) {
            User user = userRepository.findByUsername(username);
            return user;
        } else
            return null;

    }

    @Override
    public void autoLogin(String username, String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
    }

}
