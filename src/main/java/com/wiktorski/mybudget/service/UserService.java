package com.wiktorski.mybudget.Service;


import com.wiktorski.mybudget.Model.Category;
import com.wiktorski.mybudget.Model.Payment;
import com.wiktorski.mybudget.Model.User;
import com.wiktorski.mybudget.Repository.PaymentRepository;
import com.wiktorski.mybudget.Repository.RoleRepository;
import com.wiktorski.mybudget.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

//public class UserServiceImpl implements UserDetailsService {
@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;




    public void save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll())); // ALE CZEMU DAJE TUTAJ WSZYSTKIE ROLE Z BAZY? xd

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


    @Autowired
    SecurityService securityService;
    @Autowired
    PaymentRepository paymentRepository;

    public void currentUserAddPayment(String name, float amount) throws Exception {
        User user = securityService.getLoggedInUser();
        if (user != null)
            paymentRepository.save(new Payment(name, amount, user));
        else
            throw new Exception();
    }

    public List<Payment> getCurrentUserPayments() {
        return securityService.getLoggedInUser().getPayments();
    }

    public List<Category> getCurrentUserCategories() {
        return securityService.getLoggedInUser().getCategories();
    }

    /*@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByLogin(username);
        if(user==null) throw new UsernameNotFoundException(username);


        return return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),);;
    }*/
}
