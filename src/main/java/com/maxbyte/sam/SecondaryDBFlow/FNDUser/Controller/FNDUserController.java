package com.maxbyte.sam.SecondaryDBFlow.FNDUser.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Role;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.APIRequest.FNDUserRequest;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Entity.FNDUser;
import com.maxbyte.sam.SecondaryDBFlow.FNDUser.Service.FNDUserService;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fndUser")
public class FNDUserController extends CrudController<FNDUser,Integer> {

    @Autowired
    private FNDUserService fndUserService;

    private int defaultValue = 1;
    @Override
    public ServiceInterface<FNDUser, Integer> service() {
        return this.fndUserService;
    }
//    @GetMapping("")
//    public ResponseModel<List<FNDUser>> list(@RequestParam(required = false)String userName,
//                                          @RequestParam(required = false, defaultValue = "0") String requestPage){
//        return this.fndUserService.list(userName,requestPage);
//    }

    @GetMapping("")
    public ResponseModel<List<FNDUserRequest>> list(@RequestParam (required = false) String userName/*,
                                                    @RequestParam(required = false, defaultValue = "1") int page*/
                                                   ){
        return this.fndUserService.listDD(userName, defaultValue);
    }


}
