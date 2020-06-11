package com.wiktorski.mybudget.repository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository

import com.wiktorski.mybudget.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);                                                   /*WYWALA SIĘ BO MAM WIĘCEJ NIŻ JEDNEGO UŻYTKOWNIKA Z DANYM USERNAME*/  //ALBO GDY NIE MA TAKIEGO UZYTKOWNIKA

    User findByConfirmationToken(String confirmationToken);

    Optional<User> findByEmail(String email);

    List<User> findBySurname(String surname);

}
