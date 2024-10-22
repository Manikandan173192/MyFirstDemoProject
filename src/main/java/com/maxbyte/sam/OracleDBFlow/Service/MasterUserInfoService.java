//package com.maxbyte.sam.OracleDBFlow.Service;
//
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterDepartment;
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterUserInfo;
//import com.maxbyte.sam.OracleDBFlow.Repository.MasterUserInfoRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity.UserInfo;
//import com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository.UserInfoRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.EmployeeDetails;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.EmployeeDetailsRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.service.EmployeeDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//@EnableScheduling
//public class MasterUserInfoService  /*implements CommandLineRunner*/ {
//
//        @Autowired
//        private MasterUserInfoRepository masterUserInfoRepository;
//        @Autowired
//        private JdbcTemplate jdbcTemplate;
//        @Autowired
//        private EmployeeDetailsRepository employeeDetailsRepository;
//
//
////        @Override
////        public void run(String... args) throws Exception {
//////            String sql = "SELECT * FROM apps.XXHIL_EAM_CLMS_EMP_MAST_INT  OFFSET 0 ROWS FETCH NEXT 1 ROWS ONLY";
//////
//////            List<Map<String, Object>> results1 = jdbcTemplate.queryForList(sql);
//////
//////            results1.forEach(row -> {
//////                row.forEach((column, value) -> {
//////                    System.out.println(column + ": " + value);
//////                });
//////            });
////
////
////        String sql = "SELECT * FROM apps.XXHIL_EAM_CLMS_EMP_MAST_INT";
////
////
////        List<MasterUserInfo> results = jdbcTemplate.query(sql,
////                BeanPropertyRowMapper.newInstance(MasterUserInfo.class));
//////
//////          results.forEach(System.out::println);
////            System.out.println("Size User "+results.size());
////        for (MasterUserInfo employeeInfo : results) {
////            String employee = employeeInfo.getEmpCode();
////            String email = employeeInfo.getEmailId();
////
////
////            Optional<MasterUserInfo> employeeInfoOptional = masterUserInfoRepository.findByEmpCodeAndEmailId(employee, email );
////
////            MasterUserInfo masterUserInfo;
////            if (employeeInfoOptional.isPresent()) {
////                masterUserInfo=employeeInfoOptional.get();
////
////                masterUserInfo.setUNIT(employeeInfo.getUNIT());
////                masterUserInfo.setPLANT_CODE(employeeInfo.getPLANT_CODE());
////                masterUserInfo.setORGANIZATION_CODE(employeeInfo.getORGANIZATION_CODE());
////                //masterUserInfo.setEmpCode(employeeInfo.getEmpCode());
////                masterUserInfo.setTITLE_CODE(employeeInfo.getTITLE_CODE());
////                masterUserInfo.setFIRST_NAME(employeeInfo.getFIRST_NAME());
////                masterUserInfo.setMIDDLE_NAME(employeeInfo.getMIDDLE_NAME());
////                masterUserInfo.setLAST_NAME(employeeInfo.getLAST_NAME());
////                masterUserInfo.setFATHER_NAME(employeeInfo.getFATHER_NAME());
////                masterUserInfo.setGENDER(employeeInfo.getGENDER());
////                masterUserInfo.setDATE_OF_JOINING(employeeInfo.getDATE_OF_JOINING());
////                masterUserInfo.setDATE_OF_LEAVING(employeeInfo.getDATE_OF_LEAVING());
////                masterUserInfo.setDATE_OF_BIRTH(employeeInfo.getDATE_OF_BIRTH());
////                masterUserInfo.setNATIONALITY(employeeInfo.getNATIONALITY());
////                masterUserInfo.setEMPLOYEE_TYPE(employeeInfo.getEMPLOYEE_TYPE());
////                masterUserInfo.setLEAVE_REASON_CODE(employeeInfo.getLEAVE_REASON_CODE());
////                masterUserInfo.setREMARKS(employeeInfo.getREMARKS());
////                masterUserInfo.setQUALIFICATION(employeeInfo.getQUALIFICATION());
////                masterUserInfo.setGRADE_BAND(employeeInfo.getGRADE_BAND());
////                masterUserInfo.setPR_PO_TYPE(employeeInfo.getPR_PO_TYPE());
////                masterUserInfo.setPR_PO_NUMBER(employeeInfo.getPR_PO_NUMBER());
////                masterUserInfo.setCATEGORY_CODE(employeeInfo.getCATEGORY_CODE());
////                masterUserInfo.setDESIGNATION(employeeInfo.getDESIGNATION());
////                masterUserInfo.setNATURE_OF_EMPLOYMENT(employeeInfo.getNATURE_OF_EMPLOYMENT());
////                masterUserInfo.setSHIFT_TYPE(employeeInfo.getSHIFT_TYPE());
////                masterUserInfo.setOVER_TIME_ELIGIBLE(employeeInfo.getOVER_TIME_ELIGIBLE());
////                masterUserInfo.setPHONE_NUMBER(employeeInfo.getPHONE_NUMBER());
////                //masterUserInfo.setEmailId(employeeInfo.getEmailId());
////                masterUserInfo.setSKILL(employeeInfo.getSKILL());
////                masterUserInfo.setSKILL_SPECIALIZATION1(employeeInfo.getSKILL_SPECIALIZATION1());
////                masterUserInfo.setSKILL_SPECIALIZATION2(employeeInfo.getSKILL_SPECIALIZATION2());
////                masterUserInfo.setSKILL_SPECIALIZATION3(employeeInfo.getSKILL_SPECIALIZATION3());
////                masterUserInfo.setSKILL_SPECIALIZATION4(employeeInfo.getSKILL_SPECIALIZATION4());
////                masterUserInfo.setBASIC_PAY(employeeInfo.getBASIC_PAY());
////                masterUserInfo.setDA_RATE(employeeInfo.getDA_RATE());
////                masterUserInfo.setUNIT_RATE_HOUR(employeeInfo.getUNIT_RATE_HOUR());
////                masterUserInfo.setOT_UNIT_RATE_HOUR(employeeInfo.getOT_UNIT_RATE_HOUR());
////                masterUserInfo.setNIGHT_ELIGIBLE(employeeInfo.getNIGHT_ELIGIBLE());
////                masterUserInfo.setCONTRACTOR_FIRM_NAME(employeeInfo.getCONTRACTOR_FIRM_NAME());
////                masterUserInfo.setDEPARTMENT_CODE(employeeInfo.getDEPARTMENT_CODE());
////                masterUserInfo.setDEPARTMENT_DESC(employeeInfo.getDEPARTMENT_DESC());
////                masterUserInfo.setDEPARTMENT_GROUP(employeeInfo.getDEPARTMENT_GROUP());
////                masterUserInfo.setMANAGER_ID1(employeeInfo.getMANAGER_ID1());
////                masterUserInfo.setMANAGER_DEPT_ID1(employeeInfo.getMANAGER_DEPT_ID1());
////                masterUserInfo.setMANAGER_ID2(employeeInfo.getMANAGER_ID2());
////                masterUserInfo.setMANAGER_DEPT_ID2(employeeInfo.getMANAGER_DEPT_ID2());
////                masterUserInfo.setENTRY_REQUIRED(employeeInfo.getENTRY_REQUIRED());
////                masterUserInfo.setHEIGHT_PASS_AVAILABLE(employeeInfo.getHEIGHT_PASS_AVAILABLE());
////                masterUserInfo.setHEIGHT_PASS_EXPIRY_DATE(employeeInfo.getHEIGHT_PASS_EXPIRY_DATE());
////                masterUserInfo.setSPECIAL_PASS_LICENSE1(employeeInfo.getSPECIAL_PASS_LICENSE1());
////                masterUserInfo.setSPECIAL_PASS_LICENSE1_EXP_DATE(employeeInfo.getSPECIAL_PASS_LICENSE1_EXP_DATE());
////                masterUserInfo.setSPECIAL_PASS_LICENSE2(employeeInfo.getSPECIAL_PASS_LICENSE2());
////                masterUserInfo.setSPECIAL_PASS_LICENSE2_EXP_DATE(employeeInfo.getSPECIAL_PASS_LICENSE2_EXP_DATE());
////                masterUserInfo.setSPECIAL_PASS_LICENSE3(employeeInfo.getSPECIAL_PASS_LICENSE3());
////                masterUserInfo.setSPECIAL_PASS_LICENSE3_EXP_DATE(employeeInfo.getSPECIAL_PASS_LICENSE3_EXP_DATE());
////                masterUserInfo.setACTIVE_FLAG(employeeInfo.getACTIVE_FLAG());
////                masterUserInfo.setATTRIBUTE1(employeeInfo.getATTRIBUTE1());
////                masterUserInfo.setATTRIBUTE2(employeeInfo.getATTRIBUTE2());
////                masterUserInfo.setATTRIBUTE3(employeeInfo.getATTRIBUTE3());
////                masterUserInfo.setATTRIBUTE4(employeeInfo.getATTRIBUTE4());
////                masterUserInfo.setATTRIBUTE5(employeeInfo.getATTRIBUTE5());
////                masterUserInfo.setATTRIBUTE6(employeeInfo.getATTRIBUTE6());
////                masterUserInfo.setATTRIBUTE7(employeeInfo.getATTRIBUTE7());
////                masterUserInfo.setATTRIBUTE8(employeeInfo.getATTRIBUTE8());
////                masterUserInfo.setATTRIBUTE9(employeeInfo.getATTRIBUTE9());
////                masterUserInfo.setATTRIBUTE10(employeeInfo.getATTRIBUTE10());
////                masterUserInfo.setATTRIBUTE11(employeeInfo.getATTRIBUTE11());
////                masterUserInfo.setATTRIBUTE12(employeeInfo.getATTRIBUTE12());
////                masterUserInfo.setATTRIBUTE13(employeeInfo.getATTRIBUTE13());
////                masterUserInfo.setATTRIBUTE14(employeeInfo.getATTRIBUTE14());
////                masterUserInfo.setATTRIBUTE15(employeeInfo.getATTRIBUTE15());
////                masterUserInfo.setCREATION_DATE(employeeInfo.getCREATION_DATE());
////                masterUserInfo.setCREATED_BY(employeeInfo.getCREATED_BY());
////                masterUserInfo.setLAST_UPDATE_DATE(employeeInfo.getLAST_UPDATE_DATE());
////                masterUserInfo.setLAST_UPDATED_BY(employeeInfo.getLAST_UPDATED_BY());
////                masterUserInfo.setLAST_UPDATE_LOGIN(employeeInfo.getLAST_UPDATE_LOGIN());
////                masterUserInfo.setPERSON_ID(employeeInfo.getPERSON_ID());
////                masterUserInfo.setEMP_STATUS(employeeInfo.getEMP_STATUS());
////                masterUserInfo.setRESOURCE_STATUS(employeeInfo.getRESOURCE_STATUS());
////                masterUserInfo.setERR_MESSAGE(employeeInfo.getERR_MESSAGE());
////                masterUserInfo.setREQUEST_ID(employeeInfo.getREQUEST_ID());
////
////
////            } else {
////                masterUserInfo = new MasterUserInfo();
////
////                masterUserInfo.setUNIT(employeeInfo.getUNIT());
////                masterUserInfo.setPLANT_CODE(employeeInfo.getPLANT_CODE());
////                masterUserInfo.setORGANIZATION_CODE(employeeInfo.getORGANIZATION_CODE());
////                masterUserInfo.setEmpCode(employeeInfo.getEmpCode());
////                masterUserInfo.setTITLE_CODE(employeeInfo.getTITLE_CODE());
////                masterUserInfo.setFIRST_NAME(employeeInfo.getFIRST_NAME());
////                masterUserInfo.setMIDDLE_NAME(employeeInfo.getMIDDLE_NAME());
////                masterUserInfo.setLAST_NAME(employeeInfo.getLAST_NAME());
////                masterUserInfo.setFATHER_NAME(employeeInfo.getFATHER_NAME());
////                masterUserInfo.setGENDER(employeeInfo.getGENDER());
////                masterUserInfo.setDATE_OF_JOINING(employeeInfo.getDATE_OF_JOINING());
////                masterUserInfo.setDATE_OF_LEAVING(employeeInfo.getDATE_OF_LEAVING());
////                masterUserInfo.setDATE_OF_BIRTH(employeeInfo.getDATE_OF_BIRTH());
////                masterUserInfo.setNATIONALITY(employeeInfo.getNATIONALITY());
////                masterUserInfo.setEMPLOYEE_TYPE(employeeInfo.getEMPLOYEE_TYPE());
////                masterUserInfo.setLEAVE_REASON_CODE(employeeInfo.getLEAVE_REASON_CODE());
////                masterUserInfo.setREMARKS(employeeInfo.getREMARKS());
////                masterUserInfo.setQUALIFICATION(employeeInfo.getQUALIFICATION());
////                masterUserInfo.setGRADE_BAND(employeeInfo.getGRADE_BAND());
////                masterUserInfo.setPR_PO_TYPE(employeeInfo.getPR_PO_TYPE());
////                masterUserInfo.setPR_PO_NUMBER(employeeInfo.getPR_PO_NUMBER());
////                masterUserInfo.setCATEGORY_CODE(employeeInfo.getCATEGORY_CODE());
////                masterUserInfo.setDESIGNATION(employeeInfo.getDESIGNATION());
////                masterUserInfo.setNATURE_OF_EMPLOYMENT(employeeInfo.getNATURE_OF_EMPLOYMENT());
////                masterUserInfo.setSHIFT_TYPE(employeeInfo.getSHIFT_TYPE());
////                masterUserInfo.setOVER_TIME_ELIGIBLE(employeeInfo.getOVER_TIME_ELIGIBLE());
////                masterUserInfo.setPHONE_NUMBER(employeeInfo.getPHONE_NUMBER());
////                masterUserInfo.setEmailId(employeeInfo.getEmailId());
////                masterUserInfo.setSKILL(employeeInfo.getSKILL());
////                masterUserInfo.setSKILL_SPECIALIZATION1(employeeInfo.getSKILL_SPECIALIZATION1());
////                masterUserInfo.setSKILL_SPECIALIZATION2(employeeInfo.getSKILL_SPECIALIZATION2());
////                masterUserInfo.setSKILL_SPECIALIZATION3(employeeInfo.getSKILL_SPECIALIZATION3());
////                masterUserInfo.setSKILL_SPECIALIZATION4(employeeInfo.getSKILL_SPECIALIZATION4());
////                masterUserInfo.setBASIC_PAY(employeeInfo.getBASIC_PAY());
////                masterUserInfo.setDA_RATE(employeeInfo.getDA_RATE());
////                masterUserInfo.setUNIT_RATE_HOUR(employeeInfo.getUNIT_RATE_HOUR());
////                masterUserInfo.setOT_UNIT_RATE_HOUR(employeeInfo.getOT_UNIT_RATE_HOUR());
////                masterUserInfo.setNIGHT_ELIGIBLE(employeeInfo.getNIGHT_ELIGIBLE());
////                masterUserInfo.setCONTRACTOR_FIRM_NAME(employeeInfo.getCONTRACTOR_FIRM_NAME());
////                masterUserInfo.setDEPARTMENT_CODE(employeeInfo.getDEPARTMENT_CODE());
////                masterUserInfo.setDEPARTMENT_DESC(employeeInfo.getDEPARTMENT_DESC());
////                masterUserInfo.setDEPARTMENT_GROUP(employeeInfo.getDEPARTMENT_GROUP());
////                masterUserInfo.setMANAGER_ID1(employeeInfo.getMANAGER_ID1());
////                masterUserInfo.setMANAGER_DEPT_ID1(employeeInfo.getMANAGER_DEPT_ID1());
////                masterUserInfo.setMANAGER_ID2(employeeInfo.getMANAGER_ID2());
////                masterUserInfo.setMANAGER_DEPT_ID2(employeeInfo.getMANAGER_DEPT_ID2());
////                masterUserInfo.setENTRY_REQUIRED(employeeInfo.getENTRY_REQUIRED());
////                masterUserInfo.setHEIGHT_PASS_AVAILABLE(employeeInfo.getHEIGHT_PASS_AVAILABLE());
////                masterUserInfo.setHEIGHT_PASS_EXPIRY_DATE(employeeInfo.getHEIGHT_PASS_EXPIRY_DATE());
////                masterUserInfo.setSPECIAL_PASS_LICENSE1(employeeInfo.getSPECIAL_PASS_LICENSE1());
////                masterUserInfo.setSPECIAL_PASS_LICENSE1_EXP_DATE(employeeInfo.getSPECIAL_PASS_LICENSE1_EXP_DATE());
////                masterUserInfo.setSPECIAL_PASS_LICENSE2(employeeInfo.getSPECIAL_PASS_LICENSE2());
////                masterUserInfo.setSPECIAL_PASS_LICENSE2_EXP_DATE(employeeInfo.getSPECIAL_PASS_LICENSE2_EXP_DATE());
////                masterUserInfo.setSPECIAL_PASS_LICENSE3(employeeInfo.getSPECIAL_PASS_LICENSE3());
////                masterUserInfo.setSPECIAL_PASS_LICENSE3_EXP_DATE(employeeInfo.getSPECIAL_PASS_LICENSE3_EXP_DATE());
////                masterUserInfo.setACTIVE_FLAG(employeeInfo.getACTIVE_FLAG());
////                masterUserInfo.setATTRIBUTE1(employeeInfo.getATTRIBUTE1());
////                masterUserInfo.setATTRIBUTE2(employeeInfo.getATTRIBUTE2());
////                masterUserInfo.setATTRIBUTE3(employeeInfo.getATTRIBUTE3());
////                masterUserInfo.setATTRIBUTE4(employeeInfo.getATTRIBUTE4());
////                masterUserInfo.setATTRIBUTE5(employeeInfo.getATTRIBUTE5());
////                masterUserInfo.setATTRIBUTE6(employeeInfo.getATTRIBUTE6());
////                masterUserInfo.setATTRIBUTE7(employeeInfo.getATTRIBUTE7());
////                masterUserInfo.setATTRIBUTE8(employeeInfo.getATTRIBUTE8());
////                masterUserInfo.setATTRIBUTE9(employeeInfo.getATTRIBUTE9());
////                masterUserInfo.setATTRIBUTE10(employeeInfo.getATTRIBUTE10());
////                masterUserInfo.setATTRIBUTE11(employeeInfo.getATTRIBUTE11());
////                masterUserInfo.setATTRIBUTE12(employeeInfo.getATTRIBUTE12());
////                masterUserInfo.setATTRIBUTE13(employeeInfo.getATTRIBUTE13());
////                masterUserInfo.setATTRIBUTE14(employeeInfo.getATTRIBUTE14());
////                masterUserInfo.setATTRIBUTE15(employeeInfo.getATTRIBUTE15());
////                masterUserInfo.setCREATION_DATE(employeeInfo.getCREATION_DATE());
////                masterUserInfo.setCREATED_BY(employeeInfo.getCREATED_BY());
////                masterUserInfo.setLAST_UPDATE_DATE(employeeInfo.getLAST_UPDATE_DATE());
////                masterUserInfo.setLAST_UPDATED_BY(employeeInfo.getLAST_UPDATED_BY());
////                masterUserInfo.setLAST_UPDATE_LOGIN(employeeInfo.getLAST_UPDATE_LOGIN());
////                masterUserInfo.setPERSON_ID(employeeInfo.getPERSON_ID());
////                masterUserInfo.setEMP_STATUS(employeeInfo.getEMP_STATUS());
////                masterUserInfo.setRESOURCE_STATUS(employeeInfo.getRESOURCE_STATUS());
////                masterUserInfo.setERR_MESSAGE(employeeInfo.getERR_MESSAGE());
////                masterUserInfo.setREQUEST_ID(employeeInfo.getREQUEST_ID());
////
////            }
////            masterUserInfoRepository.save(masterUserInfo);
////
////        }
////    }
//
//
//
//    @Scheduled(cron = "0 */5 * * * *")
//    public void employeeSamData(){
//        List<MasterUserInfo> result =masterUserInfoRepository.findAll();
////        result.forEach(System.out::println);
//        for(MasterUserInfo result1 : result){
//            Optional<EmployeeDetails> existingEmployee = employeeDetailsRepository.findByEmpCodeAndEmailId(result1.getEmpCode(), result1.getEmailId());
//            EmployeeDetails employeeDetails;
//            if (existingEmployee.isPresent()){
//                employeeDetails = existingEmployee.get();
//
//                employeeDetails.setUNIT(result1.getUNIT());
//                employeeDetails.setPLANT_CODE(result1.getPLANT_CODE());
//                employeeDetails.setOrganizationCode(result1.getORGANIZATION_CODE());
//                employeeDetails.setTITLE_CODE(result1.getTITLE_CODE());
//                employeeDetails.setFirstName(result1.getFIRST_NAME());
//                employeeDetails.setMiddleName(result1.getMIDDLE_NAME());
//                employeeDetails.setLastName(result1.getLAST_NAME());
//                String fullName = (result1.getFIRST_NAME() != null ? result1.getFIRST_NAME() : "") +
//                        (result1.getMIDDLE_NAME() != null ? " " + result1.getMIDDLE_NAME() : "") +
//                        (result1.getLAST_NAME() != null ? " " + result1.getLAST_NAME() : "");
//                employeeDetails.setFullName(fullName.trim());
//
////                employeeDetails.setFullName(result1.getFIRST_NAME()+" "+result1.getMIDDLE_NAME()+" "+result1.getLAST_NAME());
//                employeeDetails.setFATHER_NAME(result1.getFATHER_NAME());
//                employeeDetails.setGENDER(result1.getGENDER());
//                employeeDetails.setDATE_OF_JOINING(result1.getDATE_OF_JOINING());
//                employeeDetails.setDATE_OF_LEAVING(result1.getDATE_OF_LEAVING());
//                employeeDetails.setDATE_OF_BIRTH(result1.getDATE_OF_BIRTH());
//                employeeDetails.setNATIONALITY(result1.getNATIONALITY());
//                employeeDetails.setEMPLOYEE_TYPE(result1.getEMPLOYEE_TYPE());
//                employeeDetails.setLEAVE_REASON_CODE(result1.getLEAVE_REASON_CODE());
//                employeeDetails.setREMARKS(result1.getREMARKS());
//                employeeDetails.setQUALIFICATION(result1.getQUALIFICATION());
//                employeeDetails.setGRADE_BAND(result1.getGRADE_BAND());
//                employeeDetails.setPR_PO_TYPE(result1.getPR_PO_TYPE());
//                employeeDetails.setPR_PO_NUMBER(result1.getPR_PO_NUMBER());
//                employeeDetails.setCATEGORY_CODE(result1.getCATEGORY_CODE());
//                employeeDetails.setDESIGNATION(result1.getDESIGNATION());
//                employeeDetails.setNATURE_OF_EMPLOYMENT(result1.getNATURE_OF_EMPLOYMENT());
//                employeeDetails.setSHIFT_TYPE(result1.getSHIFT_TYPE());
//                employeeDetails.setOVER_TIME_ELIGIBLE(result1.getOVER_TIME_ELIGIBLE());
//                employeeDetails.setPHONE_NUMBER(result1.getPHONE_NUMBER());
//                employeeDetails.setSKILL(result1.getSKILL());
//                employeeDetails.setSKILL_SPECIALIZATION1(result1.getSKILL_SPECIALIZATION1());
//                employeeDetails.setSKILL_SPECIALIZATION2(result1.getSKILL_SPECIALIZATION2());
//                employeeDetails.setSKILL_SPECIALIZATION3(result1.getSKILL_SPECIALIZATION3());
//                employeeDetails.setSKILL_SPECIALIZATION4(result1.getSKILL_SPECIALIZATION4());
//                employeeDetails.setBASIC_PAY(result1.getBASIC_PAY());
//                employeeDetails.setDA_RATE(result1.getDA_RATE());
//                employeeDetails.setUNIT_RATE_HOUR(result1.getUNIT_RATE_HOUR());
//                employeeDetails.setOT_UNIT_RATE_HOUR(result1.getOT_UNIT_RATE_HOUR());
//                employeeDetails.setNIGHT_ELIGIBLE(result1.getNIGHT_ELIGIBLE());
//                employeeDetails.setCONTRACTOR_FIRM_NAME(result1.getCONTRACTOR_FIRM_NAME());
//                employeeDetails.setDEPARTMENT_CODE(result1.getDEPARTMENT_CODE());
//                employeeDetails.setDEPARTMENT_DESC(result1.getDEPARTMENT_DESC());
//                employeeDetails.setDEPARTMENT_GROUP(result1.getDEPARTMENT_GROUP());
//                employeeDetails.setMANAGER_ID1(result1.getMANAGER_ID1());
//                employeeDetails.setMANAGER_DEPT_ID1(result1.getMANAGER_DEPT_ID1());
//                employeeDetails.setMANAGER_ID2(result1.getMANAGER_ID2());
//                employeeDetails.setMANAGER_DEPT_ID2(result1.getMANAGER_DEPT_ID2());
//                employeeDetails.setENTRY_REQUIRED(result1.getENTRY_REQUIRED());
//                employeeDetails.setHEIGHT_PASS_AVAILABLE(result1.getHEIGHT_PASS_AVAILABLE());
//                employeeDetails.setHEIGHT_PASS_EXPIRY_DATE(result1.getHEIGHT_PASS_EXPIRY_DATE());
//                employeeDetails.setSPECIAL_PASS_LICENSE1(result1.getSPECIAL_PASS_LICENSE1());
//                employeeDetails.setSPECIAL_PASS_LICENSE1_EXP_DATE(result1.getSPECIAL_PASS_LICENSE1_EXP_DATE());
//                employeeDetails.setSPECIAL_PASS_LICENSE2(result1.getSPECIAL_PASS_LICENSE2());
//                employeeDetails.setSPECIAL_PASS_LICENSE2_EXP_DATE(result1.getSPECIAL_PASS_LICENSE2_EXP_DATE());
//                employeeDetails.setSPECIAL_PASS_LICENSE3(result1.getSPECIAL_PASS_LICENSE3());
//                employeeDetails.setSPECIAL_PASS_LICENSE3_EXP_DATE(result1.getSPECIAL_PASS_LICENSE3_EXP_DATE());
//                employeeDetails.setACTIVE_FLAG(result1.getACTIVE_FLAG());
//                employeeDetails.setATTRIBUTE1(result1.getATTRIBUTE1());
//                employeeDetails.setATTRIBUTE2(result1.getATTRIBUTE2());
//                employeeDetails.setATTRIBUTE3(result1.getATTRIBUTE3());
//                employeeDetails.setATTRIBUTE4(result1.getATTRIBUTE4());
//                employeeDetails.setATTRIBUTE5(result1.getATTRIBUTE5());
//                employeeDetails.setATTRIBUTE6(result1.getATTRIBUTE6());
//                employeeDetails.setATTRIBUTE7(result1.getATTRIBUTE7());
//                employeeDetails.setATTRIBUTE8(result1.getATTRIBUTE8());
//                employeeDetails.setATTRIBUTE9(result1.getATTRIBUTE9());
//                employeeDetails.setATTRIBUTE10(result1.getATTRIBUTE10());
//                employeeDetails.setATTRIBUTE11(result1.getATTRIBUTE11());
//                employeeDetails.setATTRIBUTE12(result1.getATTRIBUTE12());
//                employeeDetails.setATTRIBUTE13(result1.getATTRIBUTE13());
//                employeeDetails.setATTRIBUTE14(result1.getATTRIBUTE14());
//                employeeDetails.setATTRIBUTE15(result1.getATTRIBUTE15());
//                employeeDetails.setCREATION_DATE(result1.getCREATION_DATE());
//                employeeDetails.setCREATED_BY(result1.getCREATED_BY());
//                employeeDetails.setLAST_UPDATE_DATE(result1.getLAST_UPDATE_DATE());
//                employeeDetails.setLAST_UPDATED_BY(result1.getLAST_UPDATED_BY());
//                employeeDetails.setLAST_UPDATE_LOGIN(result1.getLAST_UPDATE_LOGIN());
//                employeeDetails.setPERSON_ID(result1.getPERSON_ID());
//                employeeDetails.setEMP_STATUS(result1.getEMP_STATUS());
//                employeeDetails.setRESOURCE_STATUS(result1.getRESOURCE_STATUS());
//                employeeDetails.setERR_MESSAGE(result1.getERR_MESSAGE());
//                employeeDetails.setREQUEST_ID(result1.getREQUEST_ID());
//
//            }else {
//                employeeDetails= new EmployeeDetails();
//                employeeDetails.setUNIT(result1.getUNIT());
//                employeeDetails.setPLANT_CODE(result1.getPLANT_CODE());
//                employeeDetails.setOrganizationCode(result1.getORGANIZATION_CODE());
//                employeeDetails.setEmpCode(result1.getEmpCode());
//                employeeDetails.setTITLE_CODE(result1.getTITLE_CODE());
//                employeeDetails.setFirstName(result1.getFIRST_NAME());
//                employeeDetails.setMiddleName(result1.getMIDDLE_NAME());
//                employeeDetails.setLastName(result1.getLAST_NAME());
//                String fullName = (result1.getFIRST_NAME() != null ? result1.getFIRST_NAME() : "") +
//                        (result1.getMIDDLE_NAME() != null ? " " + result1.getMIDDLE_NAME() : "") +
//                        (result1.getLAST_NAME() != null ? " " + result1.getLAST_NAME() : "");
//                employeeDetails.setFullName(fullName.trim());
////                employeeDetails.setFullName(result1.getFIRST_NAME()+" "+result1.getMIDDLE_NAME()+" "+result1.getLAST_NAME());
//                employeeDetails.setEmailId(result1.getEmailId());
//                employeeDetails.setFATHER_NAME(result1.getFATHER_NAME());
//                employeeDetails.setGENDER(result1.getGENDER());
//                employeeDetails.setDATE_OF_JOINING(result1.getDATE_OF_JOINING());
//                employeeDetails.setDATE_OF_LEAVING(result1.getDATE_OF_LEAVING());
//                employeeDetails.setDATE_OF_BIRTH(result1.getDATE_OF_BIRTH());
//                employeeDetails.setNATIONALITY(result1.getNATIONALITY());
//                employeeDetails.setEMPLOYEE_TYPE(result1.getEMPLOYEE_TYPE());
//                employeeDetails.setLEAVE_REASON_CODE(result1.getLEAVE_REASON_CODE());
//                employeeDetails.setREMARKS(result1.getREMARKS());
//                employeeDetails.setQUALIFICATION(result1.getQUALIFICATION());
//                employeeDetails.setGRADE_BAND(result1.getGRADE_BAND());
//                employeeDetails.setPR_PO_TYPE(result1.getPR_PO_TYPE());
//                employeeDetails.setPR_PO_NUMBER(result1.getPR_PO_NUMBER());
//                employeeDetails.setCATEGORY_CODE(result1.getCATEGORY_CODE());
//                employeeDetails.setDESIGNATION(result1.getDESIGNATION());
//                employeeDetails.setNATURE_OF_EMPLOYMENT(result1.getNATURE_OF_EMPLOYMENT());
//                employeeDetails.setSHIFT_TYPE(result1.getSHIFT_TYPE());
//                employeeDetails.setOVER_TIME_ELIGIBLE(result1.getOVER_TIME_ELIGIBLE());
//                employeeDetails.setPHONE_NUMBER(result1.getPHONE_NUMBER());
//                employeeDetails.setEmailId(result1.getEmailId());
//                employeeDetails.setSKILL(result1.getSKILL());
//                employeeDetails.setSKILL_SPECIALIZATION1(result1.getSKILL_SPECIALIZATION1());
//                employeeDetails.setSKILL_SPECIALIZATION2(result1.getSKILL_SPECIALIZATION2());
//                employeeDetails.setSKILL_SPECIALIZATION3(result1.getSKILL_SPECIALIZATION3());
//                employeeDetails.setSKILL_SPECIALIZATION4(result1.getSKILL_SPECIALIZATION4());
//                employeeDetails.setBASIC_PAY(result1.getBASIC_PAY());
//                employeeDetails.setDA_RATE(result1.getDA_RATE());
//                employeeDetails.setUNIT_RATE_HOUR(result1.getUNIT_RATE_HOUR());
//                employeeDetails.setOT_UNIT_RATE_HOUR(result1.getOT_UNIT_RATE_HOUR());
//                employeeDetails.setNIGHT_ELIGIBLE(result1.getNIGHT_ELIGIBLE());
//                employeeDetails.setCONTRACTOR_FIRM_NAME(result1.getCONTRACTOR_FIRM_NAME());
//                employeeDetails.setDEPARTMENT_CODE(result1.getDEPARTMENT_CODE());
//                employeeDetails.setDEPARTMENT_DESC(result1.getDEPARTMENT_DESC());
//                employeeDetails.setDEPARTMENT_GROUP(result1.getDEPARTMENT_GROUP());
//                employeeDetails.setMANAGER_ID1(result1.getMANAGER_ID1());
//                employeeDetails.setMANAGER_DEPT_ID1(result1.getMANAGER_DEPT_ID1());
//                employeeDetails.setMANAGER_ID2(result1.getMANAGER_ID2());
//                employeeDetails.setMANAGER_DEPT_ID2(result1.getMANAGER_DEPT_ID2());
//                employeeDetails.setENTRY_REQUIRED(result1.getENTRY_REQUIRED());
//                employeeDetails.setHEIGHT_PASS_AVAILABLE(result1.getHEIGHT_PASS_AVAILABLE());
//                employeeDetails.setHEIGHT_PASS_EXPIRY_DATE(result1.getHEIGHT_PASS_EXPIRY_DATE());
//                employeeDetails.setSPECIAL_PASS_LICENSE1(result1.getSPECIAL_PASS_LICENSE1());
//                employeeDetails.setSPECIAL_PASS_LICENSE1_EXP_DATE(result1.getSPECIAL_PASS_LICENSE1_EXP_DATE());
//                employeeDetails.setSPECIAL_PASS_LICENSE2(result1.getSPECIAL_PASS_LICENSE2());
//                employeeDetails.setSPECIAL_PASS_LICENSE2_EXP_DATE(result1.getSPECIAL_PASS_LICENSE2_EXP_DATE());
//                employeeDetails.setSPECIAL_PASS_LICENSE3(result1.getSPECIAL_PASS_LICENSE3());
//                employeeDetails.setSPECIAL_PASS_LICENSE3_EXP_DATE(result1.getSPECIAL_PASS_LICENSE3_EXP_DATE());
//                employeeDetails.setACTIVE_FLAG(result1.getACTIVE_FLAG());
//                employeeDetails.setATTRIBUTE1(result1.getATTRIBUTE1());
//                employeeDetails.setATTRIBUTE2(result1.getATTRIBUTE2());
//                employeeDetails.setATTRIBUTE3(result1.getATTRIBUTE3());
//                employeeDetails.setATTRIBUTE4(result1.getATTRIBUTE4());
//                employeeDetails.setATTRIBUTE5(result1.getATTRIBUTE5());
//                employeeDetails.setATTRIBUTE6(result1.getATTRIBUTE6());
//                employeeDetails.setATTRIBUTE7(result1.getATTRIBUTE7());
//                employeeDetails.setATTRIBUTE8(result1.getATTRIBUTE8());
//                employeeDetails.setATTRIBUTE9(result1.getATTRIBUTE9());
//                employeeDetails.setATTRIBUTE10(result1.getATTRIBUTE10());
//                employeeDetails.setATTRIBUTE11(result1.getATTRIBUTE11());
//                employeeDetails.setATTRIBUTE12(result1.getATTRIBUTE12());
//                employeeDetails.setATTRIBUTE13(result1.getATTRIBUTE13());
//                employeeDetails.setATTRIBUTE14(result1.getATTRIBUTE14());
//                employeeDetails.setATTRIBUTE15(result1.getATTRIBUTE15());
//                employeeDetails.setCREATION_DATE(result1.getCREATION_DATE());
//                employeeDetails.setCREATED_BY(result1.getCREATED_BY());
//                employeeDetails.setLAST_UPDATE_DATE(result1.getLAST_UPDATE_DATE());
//                employeeDetails.setLAST_UPDATED_BY(result1.getLAST_UPDATED_BY());
//                employeeDetails.setLAST_UPDATE_LOGIN(result1.getLAST_UPDATE_LOGIN());
//                employeeDetails.setPERSON_ID(result1.getPERSON_ID());
//                employeeDetails.setEMP_STATUS(result1.getEMP_STATUS());
//                employeeDetails.setRESOURCE_STATUS(result1.getRESOURCE_STATUS());
//                employeeDetails.setERR_MESSAGE(result1.getERR_MESSAGE());
//                employeeDetails.setREQUEST_ID(result1.getREQUEST_ID());
//
//            }
//            employeeDetailsRepository.save(employeeDetails);
//        }
//
//        }
//
//
//
//
//}
