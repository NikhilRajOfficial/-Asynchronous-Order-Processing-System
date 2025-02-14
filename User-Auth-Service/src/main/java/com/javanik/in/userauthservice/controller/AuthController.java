package com.javanik.in.userauthservice.controller;


import com.javanik.in.userauthservice.dto.AuthRequest;
import com.javanik.in.userauthservice.entity.UserCredential;
import com.javanik.in.userauthservice.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

      @Autowired
    private UserAuthService userAuthService;

       @Autowired
       private AuthenticationManager authenticationManager;


        @PostMapping("/register")
        public String addNewUser(@RequestBody UserCredential userCredential)
        {
            return userAuthService.saveUser(userCredential);
        }

        @PostMapping("/token")
        public String getToken(@RequestBody AuthRequest authRequest)
        {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
            if(authenticate.isAuthenticated()) {
                return userAuthService.generateToken(authRequest.getUserName());
            }
            else
            {
                throw new RuntimeException("invalid access");
            }
        }

        @GetMapping("/validate")
        public String validateToken(@RequestParam("token") String token)
        {
             userAuthService.validateToken(token);
             return "Token is valid" ;
        }

}
