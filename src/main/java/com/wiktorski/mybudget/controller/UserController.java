package com.wiktorski.mybudget.controller;


import com.wiktorski.mybudget.model.entity.User;
import com.wiktorski.mybudget.service.EmailService;
import com.wiktorski.mybudget.service.security.SecurityService;
import com.wiktorski.mybudget.service.UserService;
import com.wiktorski.mybudget.validator.ReCaptcha.RecaptchaService;
import com.wiktorski.mybudget.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.UUID;

//TODO too much logic in controller, should be extracted to UserService or smaller methods
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    RecaptchaService captchaService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());
        return "user/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, @RequestParam(name = "g-recaptcha-response") String recaptchaResponse, BindingResult bindingResult, RedirectAttributes redir, HttpServletRequest request) {

        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "/user/registration";
        }

        //RECAPTCHA
        String ip = request.getRemoteAddr();
        String captchaVerifyMessage =
                captchaService.verifyRecaptcha(ip, recaptchaResponse);
        //if ( StringUtils.isNotEmpty(captchaVerifyMessage)) {
        if (captchaVerifyMessage != null && captchaVerifyMessage != "") {
            return "redirect:/registration";
        }

        userForm.setConfirmationToken(UUID.randomUUID().toString());
        userForm.setEnabled(false);
        userService.save(userForm);
        //securityService.autoLogin(userForm.getUsername(),userForm.getPasswordConfirm());

        SimpleMailMessage registrationEmail = emailService.createConfirmationEmail(userForm, request);
        emailService.sendEmail(registrationEmail);

        redir.addFlashAttribute(
                "confirmationMessage", "A confirmation e-mail has been sent to " + userForm.getEmail());
        return "redirect:/login";
        //return "/user/login";
    }


    //@RequestMapping( value = "/confirm", method = RequestMethod.GET )
    @Transactional
    @GetMapping("/confirm")
    public String showConfirmationPage(Model model, @RequestParam("token") String token) {
        User user = userService.findByConfirmationToken(token);
        if (user == null) {
            model.addAttribute("messageError", "You opened invalid confirmation link. ");
        } else {
            user.setEnabled(true);
            user.setConfirmationToken(null);
            if (user.isEnabled())
                model.addAttribute("confirmed", "Your account has been activated seccessfully, you can sign in now.");
            else
                model.addAttribute("confirmed", "gówno");
        }
        return "user/login";
    }

    /*/login POST controller, it is provided by Spring Security so we dont need to write this*/
    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username or password is invalid");
        if (logout != null)
            model.addAttribute("message", "You have been logged out succesfully");
        return "user/login";
    }

    @PostMapping("/loginCheckpoint")
    public String check(@RequestParam String username, @RequestParam String password, Model model, HttpServletRequest request) {
        if (username == null || username.equals("") || password == null || password.equals("")) return "/user/login";

        User user = userService.findByUsername(username);

        if (user == null) {
            model.addAttribute("messageError", "Invalid username or password");
            return "user/login";
        } else if (!encoder.matches(password, user.getPassword())) {
            emailService.sendEmail(emailService.createFailedLoginEmail(user, request));
            model.addAttribute("messageError", "Invalid username or password");
            return "user/login";
        } else if (!user.isEnabled()) {
            model.addAttribute("messageWarning", "You have to confirm your email address before signing in");
            return "user/login";
        } else {

            //emailService.sendEmail(emailService.createLogingInfoEmail(user, request));

            securityService.autoLogin(username, password);
            model.addAttribute("loggedIn", "user " + username);

           /*
            return "index";*/
           return "redirect:/";
        }
    }

    @GetMapping("/user/delete")
    public String deleteUser(){
        userService.deleteUser();
        return "redirect:/logout";
    }
}
