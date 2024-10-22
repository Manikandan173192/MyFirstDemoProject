package com.maxbyte.sam.SecondaryDBFlow.Configuration.service;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.EmployeeDetails;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository.EmployeeDetailsRepository;
import com.maxbyte.sam.SecondaryDBFlow.Response.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeDetailsService {

    @Autowired
    private EmployeeDetailsRepository employeeDetailsRepository;
    @Value("${pagination.default-size}")
    private int defaultSize;
    @Autowired
    private JdbcTemplate jdbcTemplate;


//    public ResponseModel<List<EmployeeDetails>> list(String name) {
//        ResponseModel<List<EmployeeDetails>> response;
//
//        try {
//            Pageable pageable = PageRequest.of(0, 100);
//            Page<EmployeeDetails> employeePage = employeeDetailsRepository.findByName(name, pageable);
//
//            List<EmployeeDetails> employees = employeePage.getContent();
//            if (!employees.isEmpty()) {
//                response = new ResponseModel<>(true, "Employees found.", employees);
//                System.out.println("Total Employees = "+employees.size());
//            } else {
//                response = new ResponseModel<>(true, "No employees found for the given name.", null);
//            }
//        } catch (Exception e) {
//            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
//        }
//
//        return response;
//    }
        //  ------------ >>>>>>>>> Shiva correct code  <<<<<<<<<<<<<<< ----------------
    public ResponseModel<List<EmployeeDetails>> list(String name, String userType, String organizationCode) {
        ResponseModel<List<EmployeeDetails>> response;

        try {
            Pageable pageable = PageRequest.of(0, 100,Sort.by("lastUpdateDate").descending());
//            Page<EmployeeDetails> employeePage = employeeDetailsRepository.findByNameAndContractor(name,userType, pageable);
            Page<EmployeeDetails> employeePage = employeeDetailsRepository.findByNameAndContractorFirmNameAndOrganizationCode(name,userType,organizationCode, pageable);

            List<EmployeeDetails> employees = employeePage.getContent();
            if (!employees.isEmpty()) {
                response = new ResponseModel<>(true, "Employees found.", employees);
            } else {
                response = new ResponseModel<>(true, "No employees found for the given name.", null);
            }
        } catch (Exception e) {
            response = new ResponseModel<>(false, "An error occurred while retrieving the records.", null);
        }

        return response;
    }


//    @Scheduled(cron = "0 */1 * * * *") // Run every 5 minutes
//
//    public void fetchAndSaveEmployees() throws Exception {
////    @Override
////    public void run(String... args) throws Exception{
//        try {
//            // Get the latest record's last update date from our local database
//            Optional<LocalDateTime> latestRecordOpt = Optional.ofNullable(employeeDetailsRepository.findLatestUpdateTime());
//            LocalDateTime lastUpdateDateTime = latestRecordOpt.orElse(null);
//
//            // Convert LocalDateTime to Timestamp to avoid precision issues
//            Timestamp lastUpdateTimestamp = (lastUpdateDateTime != null) ? Timestamp.valueOf(lastUpdateDateTime) : null;
//
//            // Query the external table for records updated after or at our latest update date
//            String sql = "SELECT * FROM apps.XXHIL_EAM_CLMS_EMP_MAST_INT WHERE LAST_UPDATE_DATE >= ?";
//
//            List<EmployeeDetails> newEmployees;
//
//            if (lastUpdateTimestamp != null) {
//                newEmployees = jdbcTemplate.query(
//                        sql,
//                        new Object[]{lastUpdateTimestamp},
//                        BeanPropertyRowMapper.newInstance(EmployeeDetails.class)
//                );
//            } else {
//                // If there's no last update timestamp, fetch all records
//                newEmployees = jdbcTemplate.query(
//                        "SELECT * FROM apps.XXHIL_EAM_CLMS_EMP_MAST_INT",
//                        BeanPropertyRowMapper.newInstance(EmployeeDetails.class)
//                );
//            }
//
//            // Save the fetched records to the local database
//            if (newEmployees.isEmpty()) {
//                System.out.println("********************No new Employees found.");
//            } else {
//                System.out.println("********************New Employees found: " + newEmployees.size());
//
//                for (EmployeeDetails employee : newEmployees) {
//                    // Check if the employee already exists in the local database
//                    Optional<EmployeeDetails> existingEmployeeOpt = employeeDetailsRepository.findByEmpCodeAndEmailId(employee.getEmpCode(), employee.getEmailId());
//
//                    if (existingEmployeeOpt.isPresent()) {
//                        // Update existing employee record
//                        EmployeeDetails existingEmployee = existingEmployeeOpt.get();
//                        updateEmployeeFields(existingEmployee, employee);
//                        employeeDetailsRepository.save(existingEmployee);
//                        System.out.println("Updated Employee in local DB: " + employee.getEmpCode());
//                    } else {
//                        // Save new employee to the local database
//                        employee.setId(null); // Ensure we're not setting an ID for a new record
//                        employeeDetailsRepository.save(employee);
//                        System.out.println("Saved new Employee in local DB: " + employee.getEmpCode());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // Log the error with full stack trace to identify the issue
//            e.printStackTrace();
//            System.err.println("Error fetching or updating Employee data: " + e.getMessage());
//        }
//    }
//
//    private void updateEmployeeFields(EmployeeDetails existingEmployee, EmployeeDetails newEmployee) {
//        // Update all fields except the ID
//        existingEmployee.setUnit(newEmployee.getUnit());
//        existingEmployee.setPlantCode(newEmployee.getPlantCode());
//        existingEmployee.setOrganizationCode(newEmployee.getOrganizationCode());
//        existingEmployee.setEmpCode(newEmployee.getEmpCode());
//        existingEmployee.setTitleCode(newEmployee.getTitleCode());
//        existingEmployee.setFirstName(newEmployee.getFirstName());
//        existingEmployee.setMiddleName(newEmployee.getMiddleName());
//        existingEmployee.setLastName(newEmployee.getLastName());
//        existingEmployee.setFullName(newEmployee.getFullName());
//        existingEmployee.setFatherName(newEmployee.getFatherName());
//        existingEmployee.setGender(newEmployee.getGender());
//        existingEmployee.setDateOfJoining(newEmployee.getDateOfJoining());
//        existingEmployee.setDateOfLeaving(newEmployee.getDateOfLeaving());
//        existingEmployee.setDateOfBirth(newEmployee.getDateOfBirth());
//        existingEmployee.setNationality(newEmployee.getNationality());
//        existingEmployee.setEmployeeType(newEmployee.getEmployeeType());
//        existingEmployee.setLeaveReasonCode(newEmployee.getLeaveReasonCode());
//        existingEmployee.setRemarks(newEmployee.getRemarks());
//        existingEmployee.setQualification(newEmployee.getQualification());
//        existingEmployee.setGradeBand(newEmployee.getGradeBand());
//        existingEmployee.setPrPoType(newEmployee.getPrPoType());
//        existingEmployee.setPrPoNumber(newEmployee.getPrPoNumber());
//        existingEmployee.setCategoryCode(newEmployee.getCategoryCode());
//        existingEmployee.setDesignation(newEmployee.getDesignation());
//        existingEmployee.setNatureOfEmployment(newEmployee.getNatureOfEmployment());
//        existingEmployee.setShiftType(newEmployee.getShiftType());
//        existingEmployee.setOverTimeEligible(newEmployee.getOverTimeEligible());
//        existingEmployee.setPhoneNumber(newEmployee.getPhoneNumber());
//        existingEmployee.setEmailId(newEmployee.getEmailId());
//        existingEmployee.setSkill(newEmployee.getSkill());
//        existingEmployee.setSkillSpecialization1(newEmployee.getSkillSpecialization1());
//        existingEmployee.setSkillSpecialization2(newEmployee.getSkillSpecialization2());
//        existingEmployee.setSkillSpecialization3(newEmployee.getSkillSpecialization3());
//        existingEmployee.setSkillSpecialization4(newEmployee.getSkillSpecialization4());
//        existingEmployee.setBasicPay(newEmployee.getBasicPay());
//        existingEmployee.setDaRate(newEmployee.getDaRate());
//        existingEmployee.setUnitRateHour(newEmployee.getUnitRateHour());
//        existingEmployee.setOtUnitRateHour(newEmployee.getOtUnitRateHour());
//        existingEmployee.setNightEligible(newEmployee.getNightEligible());
//        existingEmployee.setContractorFirmName(newEmployee.getContractorFirmName());
//        existingEmployee.setDepartmentCode(newEmployee.getDepartmentCode());
//        existingEmployee.setDepartmentDesc(newEmployee.getDepartmentDesc());
//        existingEmployee.setDepartmentGroup(newEmployee.getDepartmentGroup());
//        existingEmployee.setManagerId1(newEmployee.getManagerId1());
//        existingEmployee.setManagerDeptId1(newEmployee.getManagerDeptId1());
//        existingEmployee.setManagerId2(newEmployee.getManagerId2());
//        existingEmployee.setManagerDeptId2(newEmployee.getManagerDeptId2());
//        existingEmployee.setEntryRequired(newEmployee.getEntryRequired());
//        existingEmployee.setHeightPassAvailable(newEmployee.getHeightPassAvailable());
//        existingEmployee.setHeightPassExpiryDate(newEmployee.getHeightPassExpiryDate());
//        existingEmployee.setSpecialPassLicense1(newEmployee.getSpecialPassLicense1());
//        existingEmployee.setSpecialPassLicense1ExpDate(newEmployee.getSpecialPassLicense1ExpDate());
//        existingEmployee.setSpecialPassLicense2(newEmployee.getSpecialPassLicense2());
//        existingEmployee.setSpecialPassLicense2ExpDate(newEmployee.getSpecialPassLicense2ExpDate());
//        existingEmployee.setSpecialPassLicense3(newEmployee.getSpecialPassLicense3());
//        existingEmployee.setSpecialPassLicense3ExpDate(newEmployee.getSpecialPassLicense3ExpDate());
//        existingEmployee.setActiveFlag(newEmployee.getActiveFlag());
//        existingEmployee.setAttribute1(newEmployee.getAttribute1());
//        existingEmployee.setAttribute2(newEmployee.getAttribute2());
//        existingEmployee.setAttribute3(newEmployee.getAttribute3());
//        existingEmployee.setAttribute4(newEmployee.getAttribute4());
//        existingEmployee.setAttribute5(newEmployee.getAttribute5());
//        existingEmployee.setAttribute6(newEmployee.getAttribute6());
//        existingEmployee.setAttribute7(newEmployee.getAttribute7());
//        existingEmployee.setAttribute8(newEmployee.getAttribute8());
//        existingEmployee.setAttribute9(newEmployee.getAttribute9());
//        existingEmployee.setAttribute10(newEmployee.getAttribute10());
//        existingEmployee.setAttribute11(newEmployee.getAttribute11());
//        existingEmployee.setAttribute12(newEmployee.getAttribute12());
//        existingEmployee.setAttribute13(newEmployee.getAttribute13());
//        existingEmployee.setAttribute14(newEmployee.getAttribute14());
//        existingEmployee.setAttribute15(newEmployee.getAttribute15());
//        existingEmployee.setCreationDate(newEmployee.getCreationDate());
//        existingEmployee.setCreatedBy(newEmployee.getCreatedBy());
//        existingEmployee.setLastUpdateDate(newEmployee.getLastUpdateDate());
//        existingEmployee.setLastUpdatedBy(newEmployee.getLastUpdatedBy());
//        existingEmployee.setLastUpdateLogin(newEmployee.getLastUpdateLogin());
//        existingEmployee.setPersonId(newEmployee.getPersonId());
//        existingEmployee.setEmpStatus(newEmployee.getEmpStatus());
//        existingEmployee.setResourceStatus(newEmployee.getResourceStatus());
//        existingEmployee.setErrMessage(newEmployee.getErrMessage());
//        existingEmployee.setRequestId(newEmployee.getRequestId());
//    }


//    @Scheduled(cron = "0 */5 * * * *") // Run every minute
//    public void fetchAndSaveEmployees() throws Exception {
//        try {
//            // Get the latest record's last update date from our local database
//            Optional<LocalDateTime> latestRecordOpt = Optional.ofNullable(employeeDetailsRepository.findLatestUpdateTime());
//            LocalDateTime lastUpdateDateTime = latestRecordOpt.orElse(null);
//
//            // Convert LocalDateTime to Timestamp to avoid precision issues
//            Timestamp lastUpdateTimestamp = (lastUpdateDateTime != null) ? Timestamp.valueOf(lastUpdateDateTime) : null;
//
//            // Query the external table for records updated after or at our latest update date
//            String sql = "SELECT * FROM apps.XXHIL_EAM_CLMS_EMP_MAST_INT WHERE LAST_UPDATE_DATE >= ?";
//
//            List<EmployeeDetails> newEmployees;
//
//            if (lastUpdateTimestamp != null) {
//                newEmployees = jdbcTemplate.query(
//                        sql,
//                        new Object[]{lastUpdateTimestamp},
//                        BeanPropertyRowMapper.newInstance(EmployeeDetails.class)
//                );
//            } else {
//                // If there's no last update timestamp, fetch all records
//                newEmployees = jdbcTemplate.query(
//                        "SELECT * FROM apps.XXHIL_EAM_CLMS_EMP_MAST_INT",
//                        BeanPropertyRowMapper.newInstance(EmployeeDetails.class)
//                );
//            }
//
//            // Process and save the fetched records to the local database
//            if (newEmployees.isEmpty()) {
//                System.out.println("********************No new Employees found.");
//            } else {
//                System.out.println("********************New Employees found: " + newEmployees.size());
//
//                for (EmployeeDetails employee : newEmployees) {
//                    // Construct full name
//                    String fullName = (employee.getFirstName() != null ? employee.getFirstName() : "") +
//                            (employee.getMiddleName() != null ? " " + employee.getMiddleName() : "") +
//                            (employee.getLastName() != null ? " " + employee.getLastName() : "");
//                    employee.setFullName(fullName.trim());
//
//                    // Check if the employee already exists in the local database
//                    Optional<EmployeeDetails> existingEmployeeOpt = employeeDetailsRepository.findByEmpCodeAndEmailId(employee.getEmpCode(), employee.getEmailId());
//
//                    if (existingEmployeeOpt.isPresent()) {
//                        // Update existing employee record
//                        EmployeeDetails existingEmployee = existingEmployeeOpt.get();
//                        updateEmployeeFields(existingEmployee, employee);
//                        employeeDetailsRepository.save(existingEmployee);
//                        System.out.println("Updated Employee in local DB: " + employee.getEmpCode());
//                    } else {
//                        // Save new employee to the local database
//                        employee.setId(null); // Ensure we're not setting an ID for a new record
//                        employeeDetailsRepository.save(employee);
//                        System.out.println("Saved new Employee in local DB: " + employee.getEmpCode());
//                    }
//                }
//            }
//        } catch (Exception e) {
//            // Log the error with full stack trace to identify the issue
//            e.printStackTrace();
//            System.err.println("Error fetching or updating Employee data: " + e.getMessage());
//        }
//    }
//

    private void updateEmployeeFields(EmployeeDetails existingEmployee, EmployeeDetails newEmployee) {
        // Update all fields except the ID
        existingEmployee.setUnit(newEmployee.getUnit());
        existingEmployee.setPlantCode(newEmployee.getPlantCode());
        existingEmployee.setOrganizationCode(newEmployee.getOrganizationCode());
        existingEmployee.setEmpCode(newEmployee.getEmpCode());
        existingEmployee.setTitleCode(newEmployee.getTitleCode());
        existingEmployee.setFirstName(newEmployee.getFirstName());
        existingEmployee.setMiddleName(newEmployee.getMiddleName());
        existingEmployee.setLastName(newEmployee.getLastName());
        existingEmployee.setFullName(newEmployee.getFullName());
        existingEmployee.setFatherName(newEmployee.getFatherName());
        existingEmployee.setGender(newEmployee.getGender());
        existingEmployee.setDateOfJoining(newEmployee.getDateOfJoining());
        existingEmployee.setDateOfLeaving(newEmployee.getDateOfLeaving());
        existingEmployee.setDateOfBirth(newEmployee.getDateOfBirth());
        existingEmployee.setNationality(newEmployee.getNationality());
        existingEmployee.setEmployeeType(newEmployee.getEmployeeType());
        existingEmployee.setLeaveReasonCode(newEmployee.getLeaveReasonCode());
        existingEmployee.setRemarks(newEmployee.getRemarks());
        existingEmployee.setQualification(newEmployee.getQualification());
        existingEmployee.setGradeBand(newEmployee.getGradeBand());
        existingEmployee.setPrPoType(newEmployee.getPrPoType());
        existingEmployee.setPrPoNumber(newEmployee.getPrPoNumber());
        existingEmployee.setCategoryCode(newEmployee.getCategoryCode());
        existingEmployee.setDesignation(newEmployee.getDesignation());
        existingEmployee.setNatureOfEmployment(newEmployee.getNatureOfEmployment());
        existingEmployee.setShiftType(newEmployee.getShiftType());
        existingEmployee.setOverTimeEligible(newEmployee.getOverTimeEligible());
        existingEmployee.setPhoneNumber(newEmployee.getPhoneNumber());
        existingEmployee.setEmailId(newEmployee.getEmailId());
        existingEmployee.setSkill(newEmployee.getSkill());
        existingEmployee.setSkillSpecialization1(newEmployee.getSkillSpecialization1());
        existingEmployee.setSkillSpecialization2(newEmployee.getSkillSpecialization2());
        existingEmployee.setSkillSpecialization3(newEmployee.getSkillSpecialization3());
        existingEmployee.setSkillSpecialization4(newEmployee.getSkillSpecialization4());
        existingEmployee.setBasicPay(newEmployee.getBasicPay());
        existingEmployee.setDaRate(newEmployee.getDaRate());
        existingEmployee.setUnitRateHour(newEmployee.getUnitRateHour());
        existingEmployee.setOtUnitRateHour(newEmployee.getOtUnitRateHour());
        existingEmployee.setNightEligible(newEmployee.getNightEligible());
        existingEmployee.setContractorFirmName(newEmployee.getContractorFirmName());
        existingEmployee.setDepartmentCode(newEmployee.getDepartmentCode());
        existingEmployee.setDepartmentDesc(newEmployee.getDepartmentDesc());
        existingEmployee.setDepartmentGroup(newEmployee.getDepartmentGroup());
        existingEmployee.setManagerId1(newEmployee.getManagerId1());
        existingEmployee.setManagerDeptId1(newEmployee.getManagerDeptId1());
        existingEmployee.setManagerId2(newEmployee.getManagerId2());
        existingEmployee.setManagerDeptId2(newEmployee.getManagerDeptId2());
        existingEmployee.setEntryRequired(newEmployee.getEntryRequired());
        existingEmployee.setHeightPassAvailable(newEmployee.getHeightPassAvailable());
        existingEmployee.setHeightPassExpiryDate(newEmployee.getHeightPassExpiryDate());
        existingEmployee.setSpecialPassLicense1(newEmployee.getSpecialPassLicense1());
        existingEmployee.setSpecialPassLicense1ExpDate(newEmployee.getSpecialPassLicense1ExpDate());
        existingEmployee.setSpecialPassLicense2(newEmployee.getSpecialPassLicense2());
        existingEmployee.setSpecialPassLicense2ExpDate(newEmployee.getSpecialPassLicense2ExpDate());
        existingEmployee.setSpecialPassLicense3(newEmployee.getSpecialPassLicense3());
        existingEmployee.setSpecialPassLicense3ExpDate(newEmployee.getSpecialPassLicense3ExpDate());
        existingEmployee.setActiveFlag(newEmployee.getActiveFlag());
        existingEmployee.setAttribute1(newEmployee.getAttribute1());
        existingEmployee.setAttribute2(newEmployee.getAttribute2());
        existingEmployee.setAttribute3(newEmployee.getAttribute3());
        existingEmployee.setAttribute4(newEmployee.getAttribute4());
        existingEmployee.setAttribute5(newEmployee.getAttribute5());
        existingEmployee.setAttribute6(newEmployee.getAttribute6());
        existingEmployee.setAttribute7(newEmployee.getAttribute7());
        existingEmployee.setAttribute8(newEmployee.getAttribute8());
        existingEmployee.setAttribute9(newEmployee.getAttribute9());
        existingEmployee.setAttribute10(newEmployee.getAttribute10());
        existingEmployee.setAttribute11(newEmployee.getAttribute11());
        existingEmployee.setAttribute12(newEmployee.getAttribute12());
        existingEmployee.setAttribute13(newEmployee.getAttribute13());
        existingEmployee.setAttribute14(newEmployee.getAttribute14());
        existingEmployee.setAttribute15(newEmployee.getAttribute15());
        existingEmployee.setCreationDate(newEmployee.getCreationDate());
        existingEmployee.setCreatedBy(newEmployee.getCreatedBy());
        existingEmployee.setLastUpdateDate(newEmployee.getLastUpdateDate());
        existingEmployee.setLastUpdatedBy(newEmployee.getLastUpdatedBy());
        existingEmployee.setLastUpdateLogin(newEmployee.getLastUpdateLogin());
        existingEmployee.setPersonId(newEmployee.getPersonId());
        existingEmployee.setEmpStatus(newEmployee.getEmpStatus());
        existingEmployee.setResourceStatus(newEmployee.getResourceStatus());
        existingEmployee.setErrMessage(newEmployee.getErrMessage());
        existingEmployee.setRequestId(newEmployee.getRequestId());
    }


    @Scheduled(cron = "0 */5 * * * *") // Run every minute
    public void fetchAndSaveEmployees() throws Exception {
        try {
            // Query the external table for all records
            String sql = "SELECT * FROM apps.XXHIL_EAM_CLMS_EMP_MAST_INT";

            // Fetch all records from the external table
            List<EmployeeDetails> allEmployees = jdbcTemplate.query(
                    sql,
                    BeanPropertyRowMapper.newInstance(EmployeeDetails.class)
            );

            // Process and save the fetched records to the local database
            if (allEmployees.isEmpty()) {
                System.out.println("********************No Employees found.");
            } else {
                System.out.println("********************Employees found: " + allEmployees.size());

                for (EmployeeDetails employee : allEmployees) {
                    // Construct full name
                    String fullName = (employee.getFirstName() != null ? employee.getFirstName() : "") +
                            (employee.getMiddleName() != null ? " " + employee.getMiddleName() : "") +
                            (employee.getLastName() != null ? " " + employee.getLastName() : "");
                    employee.setFullName(fullName.trim());

                    // Check if the employee already exists in the local database
                    Optional<EmployeeDetails> existingEmployeeOpt = employeeDetailsRepository.findByEmpCodeAndEmailId(employee.getEmpCode(), employee.getEmailId());

                    if (existingEmployeeOpt.isPresent()) {
                        // Update existing employee record
                        EmployeeDetails existingEmployee = existingEmployeeOpt.get();
                        updateEmployeeFields(existingEmployee, employee);
                        employeeDetailsRepository.save(existingEmployee);
                        System.out.println("Updated Employee in local DB: " + employee.getEmpCode());
                    } else {
                        // Save new employee to the local database
                        employee.setId(null); // Ensure we're not setting an ID for a new record
                        employeeDetailsRepository.save(employee);
                        System.out.println("Saved new Employee in local DB: " + employee.getEmpCode());
                    }
                }
            }
        } catch (Exception e) {
            // Log the error with full stack trace to identify the issue
            e.printStackTrace();
            System.err.println("Error fetching or updating Employee data: " + e.getMessage());
        }
    }



}


