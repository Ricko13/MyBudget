package com.wiktorski.mybudget.service;


import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.repository.UserRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService extends AbstractBasicService {

    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final SecurityService securityService;
    private final EmailService emailService;

    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User findByConfirmationToken(String token) {
        return userRepo.findByConfirmationToken(token);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void deleteUser(String token) {
        if(token.equals(getCurrentUser().getConfirmationToken())) {
            userRepo.delete(securityService.getLoggedInUser());
        };
    }

    public String generateConfirmationToken() {
        User user = getCurrentUser();
        String token = UUID.randomUUID().toString();
        user.setConfirmationToken(token);
        userRepo.save(user);
        return token;
    }

    /******************************************************************************************/
    //TODO those methods should be in user entity as there are stored those informations

    public void addToBudget(float amount){
        User user = securityService.getLoggedInUser();
        user.setBudget(user.getBudget()+amount);
    }

    public void decreaseBudget(float price){
        User user = securityService.getLoggedInUser();
        user.setBudget(user.getBudget()-price);
    }

    public void decreaseBudget(float price, User user) {
        user.setBudget(user.getBudget()-price);
        userRepo.save(user);
    }

    public void setBudget(float amount) {
        User user = securityService.getLoggedInUser();
        user.setBudget(amount);
    }

    public void sendDeleteConfirmationEmail(HttpServletRequest request) {
        generateConfirmationToken();
        SimpleMailMessage deleteConfirmationEmail = emailService.createDeleteConfirmationEmail(getCurrentUser(), request);
        emailService.sendEmail(deleteConfirmationEmail);
    }


    /*@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByLogin(username);
        if(user==null) throw new UsernameNotFoundException(username);
        return return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),);;
    }*/
}
