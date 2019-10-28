package com.wiktorski.mybudget.service;


import com.wiktorski.mybudget.model.Category;
import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.model.User;
import com.wiktorski.mybudget.repository.PaymentRepository;
import com.wiktorski.mybudget.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

//public class UserServiceImpl implements UserDetailsService {
@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private SecurityService securityService;

    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll())); // daje tu wszystkie role z bazy
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

    public List<Payment> getUserPaymentsDesc() {
        List<Payment> payments=securityService.getLoggedInUser().getPayments();
        return sortPayments(payments);
    }

    public List<Category> getUserCategories() {
        return securityService.getLoggedInUser().getCategories();
    }

    private List<Payment> sortPayments(List<Payment> payments){
        Comparator<Payment> comp= (o1, o2) -> {
            if(o1.getDate().before(o2.getDate()) ){
                return 1;
            }else if(o1.getDate().after(o2.getDate()) ){
                return -1;
            }else{
                return 0;
            }
        };
        payments.sort(comp);
        return payments;
    }

    /*@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByLogin(username);
        if(user==null) throw new UsernameNotFoundException(username);
        return return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),);;
    }*/
}
