package com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserType;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.UserDetailsService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userDetails")
public class UserDetailsController extends CrudController<UserInfo,Integer> {

    @Autowired
    private UserDetailsService userDetailsService;
    @Override
    public ServiceInterface service() {
        return userDetailsService;
    }

    /*@GetMapping("")
    public ResponseModel<List<UserInfo>> list(@RequestParam(required = false) Boolean isActive){
        return this.userDetailsService.list(isActive);
    }*/

    @GetMapping("")
    public ResponseModel<List<UserInfo>> list(@RequestParam(required = false) Boolean isActive,
                                              @RequestParam(required = false) String role,
                                              @RequestParam(required = false) String department,
                                              @RequestParam(required = false) String organizationCode,
                                              @RequestParam(required = false) UserType userType,
                                              @RequestParam(required = false) String userName,
                                              @RequestParam(required = false, defaultValue = "0") String requestPage){
        return this.userDetailsService.list(isActive,role,organizationCode, department, userType,userName, requestPage);
    }

    @PostMapping("/reset-password")
    public ResponseModel<String> resetPassword(@RequestParam("newPassword") String newPassword, @RequestParam("confirmedPassword") String confirmedPassword,@RequestParam("userEmail") String userEmail) {
        return userDetailsService.resetPassword(newPassword,confirmedPassword, userEmail);
    }

    @PostMapping("/addNewUser")
    public ResponseModel<UserInfo> newUser(@RequestBody UserInfo userInfo) {
        return userDetailsService.addUser(userInfo);
    }

//    @GetMapping("/filterUser")
//    public ResponseModel<List<UserInfo>> listUserDetails(@RequestParam(required = false)String department, String organizationCode,
//                                                         @RequestParam(required = false)UserType userType,@RequestParam(required = false)String userName){
//        return this.userDetailsService.listUserDetails(department,organizationCode,userType,userName);
//    }
}
