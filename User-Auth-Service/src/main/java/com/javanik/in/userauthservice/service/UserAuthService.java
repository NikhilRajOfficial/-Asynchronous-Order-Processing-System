package com.javanik.in.userauthservice.service;


import com.javanik.in.userauthservice.entity.UserCredential;
import com.javanik.in.userauthservice.repo.UserCredentialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService  {

      @Autowired
      private UserCredentialRepo userCredentialRepo;
      @Autowired
      private PasswordEncoder passwordEncoder;

      @Autowired
      private JwtService jwtService;

       public String saveUser( UserCredential userCredential)
       {
              userCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
              userCredentialRepo.save(userCredential);
              return "USer saved Successfull";
       }

       public String generateToken(String userName)
       {
           return jwtService.generateToken(userName);
       }

       public void validateToken(String token)
       {
             jwtService.validateToken(token);
       }

}
