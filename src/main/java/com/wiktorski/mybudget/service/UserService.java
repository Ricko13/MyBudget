package com.wiktorski.mybudget.service;


import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.repository.UserRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final SecurityService securityService;

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

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public void deleteUser() {
        userRepo.delete(securityService.getLoggedInUser());
    }

    /******************************************************************************************/
    //TODO TO OTHER SERVICES???

    public void addToBudget(float amount){
        User user = securityService.getLoggedInUser();
        float tmp=user.getBudget()+amount;
        user.setBudget(tmp);
    }

    public void decreaseBudget(float price){
        User user = securityService.getLoggedInUser();
        user.setBudget(user.getBudget()-price);
    }

    public void setBudget(float amount) {
        User user = securityService.getLoggedInUser();
        user.setBudget(amount);
    }


    /*@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByLogin(username);
        if(user==null) throw new UsernameNotFoundException(username);
        return return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),);;
    }*/
}
