package com.wiktorski.mybudget.repository;

import com.wiktorski.mybudget.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);                                                   /*WYWALA SIĘ BO MAM WIĘCEJ NIŻ JEDNEGO UŻYTKOWNIKA Z DANYM USERNAME*/  //ALBO GDY NIE MA TAKIEGO UZYTKOWNIKA

    User findByConfirmationToken(String confirmationToken);

    Optional<User> findByEmail(String email);


}
