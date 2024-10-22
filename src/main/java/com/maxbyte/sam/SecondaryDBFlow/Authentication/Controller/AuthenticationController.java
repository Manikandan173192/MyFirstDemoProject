package com.maxbyte.sam.SecondaryDBFlow.Authentication.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.APIRequest.AuthRequest;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.UserDetailsService;
import com.maxbyte.sam.SecondaryDBFlow.Response.LoginResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-auth")
public class AuthenticationController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserDetailsService userDetailsService;

    @GetMapping("/public")
    public String images(){
        return "Images";
    }
    @PostMapping("/addUser")
    public ResponseModel<LoginResponse> addUser(@RequestBody UserInfo userInfo){
        return  authService.addUser(userInfo);
    }

    @PostMapping("/login")
    public ResponseModel<LoginResponse> validateUser(@RequestBody AuthRequest authRequest){
        return  authService.authenticateUser(authRequest);
    }

    @PostMapping("/forgot-password")
    public ResponseModel<LoginResponse> forgotPassword(@RequestParam("userEmail") String userEmail) {
        return authService.forgotPassword(userEmail);
    }

}
