package com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDetails {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Integer id;
//    private String UNIT;
//    private String PLANT_CODE;
//    @Column(name = "ORGANIZATION_CODE")
//    private String organizationCode;
//    @Column(name ="EMP_CODE")
//    private String empCode;
//    private String TITLE_CODE;
//    private String firstName;
//    private String middleName;
//    private String lastName;
//    private String fullName;
//    private String FATHER_NAME;
//    private String GENDER;
//    private Date DATE_OF_JOINING;
//    private Date DATE_OF_LEAVING;
//    private Date DATE_OF_BIRTH;
//    private String NATIONALITY;
//    private String EMPLOYEE_TYPE;
//    private String LEAVE_REASON_CODE;
//    private String  REMARKS;
//    private String QUALIFICATION;
//    private String GRADE_BAND;
//    private String PR_PO_TYPE;
//    private String PR_PO_NUMBER;
//    private String CATEGORY_CODE;
//    private String DESIGNATION;
//    private String NATURE_OF_EMPLOYMENT;
//    private String SHIFT_TYPE;
//    private String OVER_TIME_ELIGIBLE;
//    private Integer PHONE_NUMBER;
//    @Column(name ="EMAIL_ID")
//    private String emailId;
//    private String SKILL;
//    private String SKILL_SPECIALIZATION1;
//    private String SKILL_SPECIALIZATION2;
//    private String SKILL_SPECIALIZATION3;
//    private String SKILL_SPECIALIZATION4;
//    private Integer BASIC_PAY;
//    private Integer DA_RATE;
//    private Integer UNIT_RATE_HOUR;
//    private Integer OT_UNIT_RATE_HOUR;
//    private String NIGHT_ELIGIBLE;
//    private String CONTRACTOR_FIRM_NAME;
//    private String DEPARTMENT_CODE;
//    private String DEPARTMENT_DESC;
//    private String DEPARTMENT_GROUP;
//    private String MANAGER_ID1;
//    private String MANAGER_DEPT_ID1;
//    private String MANAGER_ID2;
//    private String MANAGER_DEPT_ID2;
//    private Integer ENTRY_REQUIRED;
//    private String HEIGHT_PASS_AVAILABLE;
//    private Date HEIGHT_PASS_EXPIRY_DATE;
//    private String SPECIAL_PASS_LICENSE1;
//    private Date SPECIAL_PASS_LICENSE1_EXP_DATE;
//    private String SPECIAL_PASS_LICENSE2;
//    private Date SPECIAL_PASS_LICENSE2_EXP_DATE;
//    private String SPECIAL_PASS_LICENSE3;
//    private Date SPECIAL_PASS_LICENSE3_EXP_DATE;
//    private String ACTIVE_FLAG;
//    private String ATTRIBUTE1;
//    private String ATTRIBUTE2;
//    private String ATTRIBUTE3;
//    private String ATTRIBUTE4;
//    private String ATTRIBUTE5;
//    private String ATTRIBUTE6;
//    private String ATTRIBUTE7;
//    private String ATTRIBUTE8;
//    private String ATTRIBUTE9;
//    private String ATTRIBUTE10;
//    private String ATTRIBUTE11;
//    private String ATTRIBUTE12;
//    private String ATTRIBUTE13;
//    private String ATTRIBUTE14;
//    private String ATTRIBUTE15;
//    private String CREATION_DATE;
//    private Integer CREATED_BY;
//    private Date LAST_UPDATE_DATE;
//    private Integer LAST_UPDATED_BY;
//    private Date LAST_UPDATE_LOGIN;
//    private Integer PERSON_ID;
//    private String EMP_STATUS;
//    private String RESOURCE_STATUS;
//    private String ERR_MESSAGE;
//    private Integer REQUEST_ID;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "UNIT")
    private String unit;

    @Column(name = "PLANT_CODE")
    private String plantCode;

    @Column(name = "ORGANIZATION_CODE")
    private String organizationCode;

    @Column(name ="EMP_CODE")
    private String empCode;

    @Column(name = "TITLE_CODE")
    private String titleCode;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "FATHER_NAME")
    private String fatherName;

    @Column(name = "GENDER")
    private String gender;

    @Column(name = "DATE_OF_JOINING")
    private String dateOfJoining;

    @Column(name = "DATE_OF_LEAVING")
    private String dateOfLeaving;

    @Column(name = "DATE_OF_BIRTH")
    private String dateOfBirth;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "EMPLOYEE_TYPE")
    private String employeeType;

    @Column(name = "LEAVE_REASON_CODE")
    private String leaveReasonCode;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "QUALIFICATION")
    private String qualification;

    @Column(name = "GRADE_BAND")
    private String gradeBand;

    @Column(name = "PR_PO_TYPE")
    private String prPoType;

    @Column(name = "PR_PO_NUMBER")
    private String prPoNumber;

    @Column(name = "CATEGORY_CODE")
    private String categoryCode;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "NATURE_OF_EMPLOYMENT")
    private String natureOfEmployment;

    @Column(name = "SHIFT_TYPE")
    private String shiftType;

    @Column(name = "OVER_TIME_ELIGIBLE")
    private String overTimeEligible;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name ="EMAIL_ID")
    private String emailId;

    @Column(name = "SKILL")
    private String skill;

    @Column(name = "SKILL_SPECIALIZATION1")
    private String skillSpecialization1;

    @Column(name = "SKILL_SPECIALIZATION2")
    private String skillSpecialization2;

    @Column(name = "SKILL_SPECIALIZATION3")
    private String skillSpecialization3;

    @Column(name = "SKILL_SPECIALIZATION4")
    private String skillSpecialization4;

    @Column(name = "BASIC_PAY")
    private String basicPay;

    @Column(name = "DA_RATE")
    private String daRate;

    @Column(name = "UNIT_RATE_HOUR")
    private String unitRateHour;

    @Column(name = "OT_UNIT_RATE_HOUR")
    private String otUnitRateHour;

    @Column(name = "NIGHT_ELIGIBLE")
    private String nightEligible;

    @Column(name = "CONTRACTOR_FIRM_NAME")
    private String contractorFirmName;

    @Column(name = "DEPARTMENT_CODE")
    private String departmentCode;

    @Column(name = "DEPARTMENT_DESC")
    private String departmentDesc;

    @Column(name = "DEPARTMENT_GROUP")
    private String departmentGroup;

    @Column(name = "MANAGER_ID1")
    private String managerId1;

    @Column(name = "MANAGER_DEPT_ID1")
    private String managerDeptId1;

    @Column(name = "MANAGER_ID2")
    private String managerId2;

    @Column(name = "MANAGER_DEPT_ID2")
    private String managerDeptId2;

    @Column(name = "ENTRY_REQUIRED")
    private String entryRequired;

    @Column(name = "HEIGHT_PASS_AVAILABLE")
    private String heightPassAvailable;

    @Column(name = "HEIGHT_PASS_EXPIRY_DATE")
    private String heightPassExpiryDate;

    @Column(name = "SPECIAL_PASS_LICENSE1")
    private String specialPassLicense1;

    @Column(name = "SPECIAL_PASS_LICENSE1_EXP_DATE")
    private String specialPassLicense1ExpDate;

    @Column(name = "SPECIAL_PASS_LICENSE2")
    private String specialPassLicense2;

    @Column(name = "SPECIAL_PASS_LICENSE2_EXP_DATE")
    private String specialPassLicense2ExpDate;

    @Column(name = "SPECIAL_PASS_LICENSE3")
    private String specialPassLicense3;

    @Column(name = "SPECIAL_PASS_LICENSE3_EXP_DATE")
    private Date specialPassLicense3ExpDate;

    @Column(name = "ACTIVE_FLAG")
    private String activeFlag;

    @Column(name = "ATTRIBUTE1")
    private String attribute1;

    @Column(name = "ATTRIBUTE2")
    private String attribute2;

    @Column(name = "ATTRIBUTE3")
    private String attribute3;

    @Column(name = "ATTRIBUTE4")
    private String attribute4;

    @Column(name = "ATTRIBUTE5")
    private String attribute5;

    @Column(name = "ATTRIBUTE6")
    private String attribute6;

    @Column(name = "ATTRIBUTE7")
    private String attribute7;

    @Column(name = "ATTRIBUTE8")
    private String attribute8;

    @Column(name = "ATTRIBUTE9")
    private String attribute9;

    @Column(name = "ATTRIBUTE10")
    private String attribute10;

    @Column(name = "ATTRIBUTE11")
    private String attribute11;

    @Column(name = "ATTRIBUTE12")
    private String attribute12;

    @Column(name = "ATTRIBUTE13")
    private String attribute13;

    @Column(name = "ATTRIBUTE14")
    private String attribute14;

    @Column(name = "ATTRIBUTE15")
    private String attribute15;

    @Column(name = "CREATION_DATE")
    private String creationDate;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "LAST_UPDATE_DATE")
    private LocalDateTime lastUpdateDate;

    @Column(name = "LAST_UPDATED_BY")
    private String lastUpdatedBy;

    @Column(name = "LAST_UPDATE_LOGIN")
    private String lastUpdateLogin;

    @Column(name = "PERSON_ID")
    private String personId;

    @Column(name = "EMP_STATUS")
    private String empStatus;

    @Column(name = "RESOURCE_STATUS")
    private String resourceStatus;

    @Column(name = "ERR_MESSAGE")
    private String errMessage;

    @Column(name = "REQUEST_ID")
    private String requestId;

}
