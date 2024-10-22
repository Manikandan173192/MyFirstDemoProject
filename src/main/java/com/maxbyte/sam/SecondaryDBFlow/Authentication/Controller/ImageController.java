package com.maxbyte.sam.SecondaryDBFlow.Authentication.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Authentication.APIRequest.AuthRequest;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
import com.maxbyte.sam.SecondaryDBFlow.Authentication.Service.AuthService;
import com.maxbyte.sam.SecondaryDBFlow.Response.LoginResponse;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/public")
public class ImageController {
    @GetMapping("/images")
    public String images(){
        return "6e9a89e6-0cab-4232-8fff-44d121795059_2007954-200.png";
    }

}
