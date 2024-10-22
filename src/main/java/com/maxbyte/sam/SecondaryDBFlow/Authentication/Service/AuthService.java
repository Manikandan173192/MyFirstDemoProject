package com.maxbyte.sam.SecondaryDBFlow.Authentication.Service;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.APIRequest.AuthRequest;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.JWT.JwtService;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.Response.LoginResponse;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public ResponseModel<LoginResponse> addUser(UserInfo userInfo) {
        try {
            userInfo.setUserPassword(passwordEncoder.encode(userInfo.getUserPassword()));
            repository.save(userInfo);
            LoginResponse response = new LoginResponse();
            response.setUserName(userInfo.getUserName());
            response.setUserRole(userInfo.getRole());
            response.setOrganizationCode(userInfo.getOrganizationCode());
            response.setUserId(userInfo.getUserId().toString());

            response.setToken(jwtService.generateToken(userInfo.getUserName()));
            return new ResponseModel<>(true, "User Added Successfully",response);

        }catch (Exception e){
            return new ResponseModel<>(false, "Failed to add",null);
        }
    }

    public ResponseModel<LoginResponse> authenticateUser(AuthRequest authRequest) {
        try{
            Optional<UserInfo> userData = repository.findByuserName(authRequest.getUserName());

            if(userData.get().getUserName().equals(authRequest.getUserName())){
                if(userData!=null && userData.get().isActive()){
                    Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName() ,authRequest.getUserPassword()));
                    LoginResponse response = new LoginResponse();
                    response.setUserName(userData.get().getUserName());
                    response.setUserRole(userData.get().getRole());
                    response.setOrganizationCode(userData.get().getOrganizationCode());
                    response.setUserId(userData.get().getId().toString());
                    response.setOrganizationId(userData.get().getOrganizationId());
                    response.setToken(jwtService.generateToken(authRequest.getUserName()));
                    return new ResponseModel<>(true, "Login Successfully",response);
                }else{
                    return new ResponseModel<>(false, "User is not active!",null);
                }
            }else{
                return new ResponseModel<>(false, "Invalid User name/ User password",null);
            }

        }catch (Exception e){
            return new ResponseModel<>(false, "Invalid User name/ User password",null);
        }
    }

    public ResponseModel<LoginResponse> forgotPassword(String userEmail) {
        try {
            Optional<UserInfo> userData = repository.findByUserEmail(userEmail);

            if (userData.isPresent() && userData.get().isActive()) {

                /*LoginResponse loginResponse= new LoginResponse();
                loginResponse.setToken(jwtService.generate5MinsToken(userData.get().getUserName()));

                return new ResponseModel<>(true, "Password will be expired within 5 minutes.", loginResponse);
          */
                try {

                    var token = jwtService.generate5MinsToken(userData.get().getUserName());
                    // Creating a simple mail message
                    SimpleMailMessage mailMessage = new SimpleMailMessage();

                    // Setting up necessary details
                    mailMessage.setFrom(sender);
                    mailMessage.setTo(userData.get().getUserEmail());
                    mailMessage.setText("http://localhost:3000/createnewpassword?t="+ token+"&e="+userData.get().getUserEmail());
                    mailMessage.setSubject("Reset password link");

                    // Sending the mail
                    javaMailSender.send(mailMessage);
                    return new ResponseModel<>(true, "Email sent", null);
                }

                // Catch block to handle the exceptions
                catch (Exception e) {
                    return new ResponseModel<>(true, "Email not sent", null);
                }
            } else {
                return new ResponseModel<>(false, "User not found or is not active!", null);
            }
        } catch (Exception e) {
            return new ResponseModel<>(false, "Failed to initiate password reset.", null);
        }
    }
}
