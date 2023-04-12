package com.example.loginmicroservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class LoginController {

    @Value("${url.user.microservice.getUsers}")
    private String getUsersPath;

    @Value("${url.user.microservice.addUser}")
    private String addUserPath;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/login")
    public LoginDetails getEmployees(@RequestBody User loginuser) throws InterruptedException {

        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(getUsersPath, User[].class);
        List<User> users = Arrays.stream(responseEntity.getBody()).collect(Collectors.toList());

        logger.info("a request received to login a user  xxxx..!!");
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
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(getUsersPath, User[].class);
        List<User> users = Arrays.stream(responseEntity.getBody()).collect(Collectors.toList());


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

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<User> entity = new HttpEntity<User>(newUser,headers);

             restTemplate.exchange(
                    addUserPath, HttpMethod.POST, entity, User.class).getBody();

        }

        return registrationDetails;

    }

    @GetMapping("/getUsers")
    public List<User> getEmployees() throws InterruptedException {
        logger.info("a request received to get all employees");
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(getUsersPath, User[].class);
        List<User> users = Arrays.stream(responseEntity.getBody()).collect(Collectors.toList());


        TimeUnit.SECONDS.sleep(60);
        return users;
    }


}
