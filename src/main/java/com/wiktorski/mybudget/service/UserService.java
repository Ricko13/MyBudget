package com.wiktorski.mybudget.service;


import com.wiktorski.mybudget.model.Category;
import com.wiktorski.mybudget.model.Payment;
import com.wiktorski.mybudget.model.User;
import com.wiktorski.mybudget.repository.UserRepository;
import com.wiktorski.mybudget.service.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//public class UserServiceImpl implements UserDetailsService {
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder encoder;
    private final SecurityService securityService;

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
        List<Payment> payments = securityService.getLoggedInUser().getPayments();
        for (Payment p : payments) {
            Date date = p.getDate();
            p.setJustDate(new SimpleDateFormat("dd-MM-yyyy").format(date));
            p.setJustTime(new SimpleDateFormat("HH:mm").format(date));
        }
        return sortPayments(payments);
    }

    public List<Category> getUserCategories() {
        return securityService.getLoggedInUser().getCategories();
    }

    private List<Payment> sortPayments(List<Payment> payments) {
        Comparator<Payment> comp = (o1, o2) -> {
            if (o1.getDate().before(o2.getDate())) {
                return 1;
            } else if (o1.getDate().after(o2.getDate())) {
                return -1;
            } else {
                return 0;
            }
        };
        payments.sort(comp);
        return payments;
    }

    public void deleteUser() {
        userRepo.delete(securityService.getLoggedInUser());
    }

    public void setBudget(float amount) {
        User user = securityService.getLoggedInUser();
        user.setBudget(amount);
    }

    public void addToBudget(float amount){
        User user = securityService.getLoggedInUser();
        float tmp=user.getBudget()+amount;
        user.setBudget(tmp);
    }

    public void decreaseBudget(float price){
        User user = securityService.getLoggedInUser();
        user.setBudget(user.getBudget()-price);
    }



    /*@Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepo.findByLogin(username);
        if(user==null) throw new UsernameNotFoundException(username);
        return return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),);;
    }*/
}
