package com.yigiter.controller;


import com.yigiter.dto.LoginRequest;
import com.yigiter.dto.LoginResponse;
import com.yigiter.dto.RegisterRequest;
import com.yigiter.dto.response.ResponseMessage;
import com.yigiter.dto.response.TResponse;
import com.yigiter.security.jwt.JwtUtils;
import com.yigiter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserJwtController {

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;

    // Register
    @PostMapping("/register")
    public ResponseEntity<TResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
        userService.saveUser(registerRequest);
        TResponse response = new TResponse();
        response.setMessage(ResponseMessage.REGİSTER_RESPONSE_MESSAGE);
        response.setSuccess(true);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

     //Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest){


        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());


        Authentication authentication= authenticationManager.authenticate(usernamePasswordAuthenticationToken);


        UserDetails userDetails= (UserDetails) authentication.getPrincipal();
        String jwttoken= jwtUtils.generateJwtToken(userDetails);

        LoginResponse loginResponse=new LoginResponse(jwttoken);
        return new ResponseEntity<>(loginResponse,HttpStatus.OK);
    }

    
}
