package com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Controller;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Controller.CrudController;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Interface.ServiceInterface;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetWorkOrderListByWorkOrderNo;
import com.maxbyte.sam.SecondaryDBFlow.WorkOrder.APIRequest.GetWorkOrderListEAM;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest.GetWorkRequestListByWorkRequestNo;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest.GetWorkRequestListEAM;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest.UpdateWorkRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.APIRequest.AddWorkRequest1;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Entity.WorkRequest;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Repository.WorkRequestRepository;
import com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Service.WorkRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/workRequest")
public class WorkRequestController extends CrudController<WorkRequest,Integer> {

    @Autowired
    private WorkRequestService workRequestService;
    @Override
    public ServiceInterface service() {
        return workRequestService;
    }
   /* @Autowired
    private MasterWorkRequestService masterWorkRequestService;*/
    @Autowired
    private WorkRequestRepository workRequestRepository;
    @Autowired
    private RestTemplate restTemplate;
//    @Autowired
//    private WebClient webClient;

   /* @GetMapping("")
    public ResponseModel<List<WorkRequest>> list(@RequestParam(required = false)Integer WRNumber,
                          @RequestParam(required = false)  String assetNumber,
                          @RequestParam(required = false) String department,
                          @RequestParam(required = false) String organizationId,
                                                 @RequestParam(required = false, defaultValue = "0") String requestPage){
        //String sql = "select * from APPS.WIP_EAM_WORK_REQUESTS_v where CREATION_DATE between [[IP_StartDate]] and [[IP_EndDate]] AND ORGANIZATION_ID = [[IP_OrganisationCode]] ORDER BY CREATION_DATE DESC";
        return this.workRequestService.list(WRNumber, assetNumber,
                department,requestPage, organizationId);
    }*/


//     @GetMapping("")
//    public ResponseModel<List<WorkRequest>> list(@RequestParam(required = false)String WRNumber,
//                          @RequestParam(required = false)  String assetNumber,
//                          @RequestParam(required = false) String department,
//                          @RequestParam(required = false) String organizationId,
//                          @RequestParam(required = false)  String workRequestStatus,
//                                                 @RequestParam(required = false, defaultValue = "0") String requestPage){
//        return this.workRequestService.list(WRNumber, assetNumber, department, organizationId,workRequestStatus,requestPage);
//    }

//    @GetMapping("/findByDateTime")
//    public ResponseModel<List<WorkRequest>> findWorkRequestBetweenDateTime(@RequestParam(required = false) LocalDateTime from,
//                                                                           @RequestParam(required = false) LocalDateTime to,
//                                                                           @RequestParam(required = false, defaultValue = "0") String requestPage,
//                                                                           @RequestParam(required = false) String organizationId) {
//        return this.workRequestService.findWorkRequestByDateTime(organizationId,from, to, requestPage);
//    }

    @PostMapping("/addWorkRequest")
    public ResponseEntity<ResponseModel<String>> addWorkRequest(@RequestBody AddWorkRequest1 data) {
        return workRequestService.addWorkRequest(data);
    }

    @PutMapping("/updateWorkRequest")
    public ResponseEntity<ResponseModel<String>> updateWorkRequest(@RequestBody UpdateWorkRequest data) {
        return workRequestService.updateWorkRequest(data);
    }

    @GetMapping("/getWorkRequestListEAM")
    public  ResponseModel<List<GetWorkRequestListEAM>> listWorkRequest(@RequestParam String startDate, @RequestParam String endDate, @RequestParam String organizationId){
        return workRequestService.listWorkRequest(startDate,endDate,organizationId);
    }

    @GetMapping("/getWorkRequestListByWorkRequestNumber")
    public  ResponseModel<List<GetWorkRequestListByWorkRequestNo>> listWorkRequest(@RequestParam String workRequestNumber){
        return workRequestService.listWorkRequestListByWorkRequestNo(workRequestNumber);
    }

    @GetMapping("/searchWorkRequestNumber")
    public  ResponseModel<List<String>> getSearchWorkRequestNumber(@RequestParam(required = false) String workRequestNumber){
        return workRequestService.getSearchWorkRequestNumber(workRequestNumber);
    }

    @GetMapping("/searchFilters")
    public  ResponseModel<List<GetWorkRequestListEAM>> getSearchFilters(@RequestParam(required = false) String workRequestNumber,
                                                                        @RequestParam(required = false) String assetNumber,
                                                                        @RequestParam(required = false) String department,
                                                                        @RequestParam(required = false) String status,
                                                                        @RequestParam(required = false) String additionalDescription){
        return workRequestService.getSearchFilters(workRequestNumber,assetNumber,department,status,additionalDescription);
    }


   /* @PostMapping("/addWorkRequest")
    public ResponseEntity<ResponseModel<WorkRequest>> addWorkRequest(@Valid @RequestBody AddWorkRequest1 data) {
        return workRequestService.addWorkRequest(data);
    }*/


   /* @PostMapping("/updateWorkRequest/{wrNumber}")
    public ResponseEntity<ResponseModel<WorkRequest>> updateWorkRequestData(@PathVariable String wrNumber, @RequestBody AddWorkRequest1 data){
        return workRequestService.updateWorkRequest(wrNumber,data);
    }*/




}
