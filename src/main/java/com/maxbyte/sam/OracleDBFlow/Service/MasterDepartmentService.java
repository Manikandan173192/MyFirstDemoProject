//package com.maxbyte.sam.OracleDBFlow.Service;
//
//import com.maxbyte.sam.OracleDBFlow.Entity.MasterDepartment;
//import com.maxbyte.sam.OracleDBFlow.Repository.MasterDepartmentRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Department;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.DepartmentRepository;
//import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.OrganizationRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.scheduling.annotation.EnableScheduling;
//
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@EnableScheduling
//public class MasterDepartmentService implements CommandLineRunner {
//    @Autowired
//    private MasterDepartmentRepository masterDepartmentRepository;
//    @Autowired
//    private DepartmentRepository departmentRepository;
//    @Autowired
//    private OrganizationRepository organizationRepository;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Override
//    public void run(String... args) throws Exception {
////        String sql = "SELECT * FROM Department";
//        String sql = "select * from apps.XXHIL_EAM_DEPT_V";
//
//        List<MasterDepartment> results = jdbcTemplate.query(sql,
//                BeanPropertyRowMapper.newInstance(MasterDepartment.class));
//
//
//        // results.forEach(System.out::println);
//        System.out.println("The size of the department data = " + results.size());
//
//        for (MasterDepartment departments : results) {
//            Integer department = departments.getDepartmentId();
//
//            Optional<MasterDepartment> departmentsOptional = masterDepartmentRepository.findByDepartmentId(department);
//            MasterDepartment depart;
//            if (departmentsOptional.isPresent()) {
//                //MasterDepartment depart = new MasterDepartment();
//                depart = departmentsOptional.get();
////                depart.setDepartmentId(departments.getDepartmentId());
//                depart.setDEPARTMENT_CODE(departments.getDEPARTMENT_CODE());
//                depart.setDESCRIPTION(departments.getDESCRIPTION());
//                depart.setLOCATION_ID(departments.getLOCATION_ID());
//                depart.setLOCATION_CODE(departments.getLOCATION_CODE());
//                depart.setLOCATION_DESCRIPTION(departments.getLOCATION_DESCRIPTION());
//                depart.setMAINT_COST_CATEGORY_VALUE(departments.getMAINT_COST_CATEGORY_VALUE());
//                depart.setORGANIZATION_ID(departments.getORGANIZATION_ID());
//                depart.setORGANIZATION_CODE(departments.getORGANIZATION_CODE());
//
//            } else {
//                depart = new MasterDepartment();
//
//                depart.setDepartmentId(departments.getDepartmentId());
//                depart.setDEPARTMENT_CODE(departments.getDEPARTMENT_CODE());
//                depart.setDESCRIPTION(departments.getDESCRIPTION());
//                depart.setLOCATION_ID(departments.getLOCATION_ID());
//                depart.setLOCATION_CODE(departments.getLOCATION_CODE());
//                depart.setLOCATION_DESCRIPTION(departments.getLOCATION_DESCRIPTION());
//                depart.setMAINT_COST_CATEGORY_VALUE(departments.getMAINT_COST_CATEGORY_VALUE());
//                depart.setORGANIZATION_ID(departments.getORGANIZATION_ID());
//                depart.setORGANIZATION_CODE(departments.getORGANIZATION_CODE());
//            }
//            masterDepartmentRepository.save(depart);
//        }
//    }
////
////
////
////    //@Override
//////    public void run(String... args) throws Exception {
//   @Scheduled(cron = "0 */5 * * * *")
//        public void departmentData(){
//            List<MasterDepartment> result =masterDepartmentRepository.findAll();
//           // result.forEach(System.out::println);
//            for(MasterDepartment result1 : result){
//                Optional<Department> existingDepartment = departmentRepository.findByDepartmentId(result1.getDepartmentId());
//                Department newDepartment;
//                if (existingDepartment.isPresent()){
//                    newDepartment = existingDepartment.get();
//                    newDepartment.setDepartment(result1.getDEPARTMENT_CODE());
//                    newDepartment.setDepartmentId(result1.getDepartmentId());
//                    newDepartment.setDepartmentDescription(result1.getDESCRIPTION());
//                    newDepartment.setLOCATION_CODE(result1.getLOCATION_CODE());
//                    newDepartment.setLOCATION_ID(result1.getLOCATION_ID());
//                    newDepartment.setLOCATION_DESCRIPTION(result1.getLOCATION_DESCRIPTION());
//                    newDepartment.setMAINT_COST_CATEGORY_VALUE(result1.getMAINT_COST_CATEGORY_VALUE());
//                    newDepartment.setIsActive(true);
//
//                    //System.out.println("Department rfjcj" + result1.getDepartmentId() + " already exists.");
//
//                }else {
//                    newDepartment = new Department();
//                    newDepartment.setDepartment(result1.getDEPARTMENT_CODE());
//                    newDepartment.setDepartmentId(result1.getDepartmentId());
//                    newDepartment.setDepartmentDescription(result1.getDESCRIPTION());
//                    newDepartment.setLOCATION_CODE(result1.getLOCATION_CODE());
//                    newDepartment.setLOCATION_ID(result1.getLOCATION_ID());
//                    newDepartment.setLOCATION_DESCRIPTION(result1.getLOCATION_DESCRIPTION());
//                    newDepartment.setMAINT_COST_CATEGORY_VALUE(result1.getMAINT_COST_CATEGORY_VALUE());
//                  //  newDepartment.setIsActive(true);
//
//                }
//                departmentRepository.save(newDepartment);
//            }
//
//        }
//
//        //>>>>>>>>>>>>>>>>>>>> Fetching Organisation data from oracle db <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//        @Scheduled(cron = "0 */5 * * * *")
//        public void organisationData(){
//            List<MasterDepartment> result =masterDepartmentRepository.findAll();
//            // result.forEach(System.out::println);
//
//            for (MasterDepartment masterDepartment : result){
//                Optional<Organization> existingOrganization = organizationRepository.findByOrganizationId(masterDepartment.getORGANIZATION_ID());
//                Organization newOrganization;
//                if (existingOrganization.isPresent()){
//                    newOrganization = existingOrganization.get();
////                    newOrganization.setOrganizationId(masterDepartment.getORGANIZATION_ID());
//                    newOrganization.setOrganizationCode(masterDepartment.getORGANIZATION_CODE());
//                    newOrganization.setOrganizationDescription(masterDepartment.getDESCRIPTION());
//                    newOrganization.setIsActive(true);
//                }else {
//                    newOrganization = new Organization();
//                    newOrganization.setOrganizationId(masterDepartment.getORGANIZATION_ID());
//                    newOrganization.setOrganizationCode(masterDepartment.getORGANIZATION_CODE());
//                    newOrganization.setOrganizationDescription(masterDepartment.getDESCRIPTION());
//                    newOrganization.setIsActive(true);
//                }
//                organizationRepository.save(newOrganization);
//            }
//        }
//
//
//
//    }
