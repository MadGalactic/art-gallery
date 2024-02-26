package org.launchcode.artgallery.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.launchcode.artgallery.data.UserRepository;
import org.launchcode.artgallery.models.User;
import org.launchcode.artgallery.models.dto.LoginFormDTO;
import org.launchcode.artgallery.models.dto.RegistrationFormDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AuthenticationController {

    @Autowired
    private UserRepository userRepository;

    private static final String userSessionKey = "user";

    private static void setUserInSession(HttpSession session, User user) {
        session.setAttribute(userSessionKey, user.getId());

    }

    public User getUserFromSession(HttpSession session) {

        // Cast the integer for it to be used. Gets the user associated with the session key
        Integer userId = (Integer) session.getAttribute(userSessionKey);
        // if a userId is not found with the associated with the user session key, return null. this early return will prevent the logic from continuing
        if (userId == null) {
            return null;
        }

        // in the event that there is or isn't a user, use optional. If a user is found with the id that's passed in, it can be returned
        Optional<User> userOpt = userRepository.findById(userId);
        // check if the databse has what we are looking for. if the user came back empty, return null.
        if (userOpt.isEmpty()) {
            return null;
        }
        // if a user is found, unbox from the optional class and return user
        return userOpt.get();

    }

    // renders registration form
    @GetMapping("/register")
    public String displayRegistrationForm(Model model, HttpSession session) {
        model.addAttribute(new RegistrationFormDTO());
        // TODO: send value of logged in boolean
        return "register";
    }

    // handles registration form submission
    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute @Valid RegistrationFormDTO registrationFormDTO, Errors errors, HttpServletRequest request) {

        // send user back to the form if efforts are found

        if (errors.hasErrors()) {
            return "register";
        }

        // get the username from the registrationFormDTO object that was created with data from the form that's coming in as query params. go lookup the username from the databse. If something comes back, that's the existing user
        User existingUser = userRepository.findByUsername(registrationFormDTO.getUsername());

        if (existingUser != null) {
            errors.rejectValue("username", "username.alreadyExists", "A user with that username already exists");
            return "register";
        }

        String password = registrationFormDTO.getPassword();
        String verifyPassword = registrationFormDTO.getVerifyPassword();
        if (!password.equals(verifyPassword)) {
            errors.rejectValue("password", "passwords.mismatch", "Passwords do not match");
            return "register";
        }

        // save the new username & password, start a new session, and redirect to home page
        User newUser = new User(registrationFormDTO.getUsername(), registrationFormDTO.getPassword());
        userRepository.save(newUser);
        // this starts the session and prevents the user from having to login after immediately registering
        setUserInSession(request.getSession(), newUser);
        return "redirect:/artworks";

    }

    @GetMapping("/login")
        public String displayLoginForm(Model model, HttpSession session) {
        // calling the loginFormDTO is implicitly creating a new login in form
        model.addAttribute(new LoginFormDTO());
        return "login";
        }

    @PostMapping("/login")
    public String processLoginForm(@ModelAttribute @Valid LoginFormDTO loginFormDTO, Errors errors, HttpServletRequest request) {
        if (errors.hasErrors()) {
            return "login";
        }
        User theUser = userRepository.findByUsername(loginFormDTO.getUsername());
        // stores the password when they submitted it
        String password = loginFormDTO.getPassword();

        if (theUser == null || !theUser.isMatchingPassword(password)) {
            errors.rejectValue(
                    "password",
                    "login.invalid",
                    "Credentials invalid. Please try again with correct username/password"
            );
            return "login";
        }
        setUserInSession(request.getSession(), theUser);
        return "redirect:/artworks";
    }

    @GetMapping("/logout")
        public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }



}
