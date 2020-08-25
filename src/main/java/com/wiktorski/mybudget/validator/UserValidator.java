package com.wiktorski.mybudget.validator;


import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;

        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate.userForm.username");
            System.err.println("Validation error - duplicate username");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");

        if (user.getUsername().length() < 4 || user.getUsername().length() > 32){
            errors.rejectValue("username", "Size.userForm.username");
            System.err.println("Username wrong length");
        }

        /*if(userService.findByEmail(user.getEmail()) != null)
            errors.rejectValue("email", "Duplicate.userForm.email");*/
//        userService.findByEmail(user.getEmail()).ifPresent(user1 -> errors.rejectValue("email", "Duplicate.userForm.email"));


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");

        if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size.userForm.password");
            System.err.println("Wrong password length");
        } else if (!containsSpecialChar(user.getPassword())) {
            errors.rejectValue("password", "Size.userForm.password");
            System.err.println("Password not caontaining special char");
        } else if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
            System.err.println("Password and password confirmation differs each other");
        }
    }

    private boolean containsSpecialChar(String password) {
        if (password.length() < 8)
            return false;

        Pattern upperCase = Pattern.compile("[A-z]");
        Pattern lowerCase = Pattern.compile("[a-z]");
        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasUpperCase = upperCase.matcher(password);
        Matcher hasLowerCase = lowerCase.matcher(password);
        Matcher hasDigit = digit.matcher(password);
        Matcher hasSpecial = special.matcher(password);

        return hasUpperCase.find() && hasLowerCase.find() && hasDigit.find() && hasSpecial.find();


    }
}
