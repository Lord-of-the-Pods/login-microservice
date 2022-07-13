package com.example.loginmicroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController {


    Logger logger = LoggerFactory.getLogger(LoginController.class);

    private static List<User> users = new ArrayList<>();

    static{
        User user = new User() ;
        user.setFirstName("arushi");
        user.setLastName("vishnoi");
        user.setEmail("abhishek.vishnoi@hotmail.com");
        user.setPassword("password1");
        users.add(user);

    }


    @PostMapping("/login")
    public LoginDetails getEmployees(@RequestBody User loginuser){
        logger.info("a request received to login a user..!!");
        User loggedInUser = new User();
        LoginDetails loginDetails = new LoginDetails();
        loginDetails.setLoginSuccess(false);

        for(User user : users) {
            if(loginuser.getEmail().equals(user.getEmail())){
                if(loginuser.getPassword().equals(user.getPassword())){
                    loginDetails.setLoginSuccess(true);
                    loginDetails.setLoggedinUser(user);
                    loginDetails.setMessage("Login is Success!");
                    logger.info("login is successful..!!");
                    break;
                }
                logger.info("login is not successful..!!");
                loginDetails.setMessage("Incorrect credentials!!");
            }
        }

        return loginDetails;
    }

    @PostMapping("/registerUser")
    public RegistrationDetails addEmployee(@RequestBody User newUser) {
        logger.info("a request received to add a new employee");

        RegistrationDetails registrationDetails = new RegistrationDetails();
        registrationDetails.setRegistrationSuccess(false);

        boolean userAlreadyExists = false;
        for(User user : users) {
            if(newUser.getEmail().equals(user.getEmail())){
                registrationDetails.setMessage("a user already exists with same emailID!!");
                registrationDetails.setUser(newUser);
                userAlreadyExists=true;
                break;
            }
        }

        if(!userAlreadyExists){
            registrationDetails.setMessage("registration done successfully!!");
            registrationDetails.setUser(newUser);
            registrationDetails.setRegistrationSuccess(true);
            users.add(newUser);
        }

        return registrationDetails;

    }


    @GetMapping("/getUsers")
    public List<User> getEmployees(){
        logger.info("a request received to get all employees");
        return users;
    }

    @GetMapping("/forgetPassword")
    public String forgetPassword(@RequestParam String email){
        logger.info("an email has been sent to reset the password" + email);

        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            return "email sent";
        }
        else{
            return "incorrect email entered";
        }

    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestBody ChangePassword password){
        logger.info("a request received to login a user..!!");
            if((password.getOldPassword().equals(password.getNewPassword()))) {
                logger.info("password change is successful");
                return "password not changed data";
            }
        return "password changed";
    }
}
