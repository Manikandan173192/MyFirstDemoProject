package com.maxbyte.sam.OracleDBFlow.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
public class MasterUserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String UNIT;
    private String PLANT_CODE;
    private String ORGANIZATION_CODE;
    @Column(name ="EMP_CODE")
    private String empCode;
    private String TITLE_CODE;
    private String FIRST_NAME;
    private String MIDDLE_NAME;
    private String LAST_NAME;
    private String FATHER_NAME;
    private String GENDER;
    private Date DATE_OF_JOINING;
    private Date DATE_OF_LEAVING;
    private Date DATE_OF_BIRTH;
    private String NATIONALITY;
    private String EMPLOYEE_TYPE;
    private String LEAVE_REASON_CODE;
    private String  REMARKS;
    private String QUALIFICATION;
    private String GRADE_BAND;
    private String PR_PO_TYPE;
    private String PR_PO_NUMBER;
    private String CATEGORY_CODE;
    private String DESIGNATION;
    private String NATURE_OF_EMPLOYMENT;
    private String SHIFT_TYPE;
    private String OVER_TIME_ELIGIBLE;
    private Integer PHONE_NUMBER;
    @Column(name ="EMAIL_ID")
    private String emailId;
    private String SKILL;
    private String SKILL_SPECIALIZATION1;
    private String SKILL_SPECIALIZATION2;
    private String SKILL_SPECIALIZATION3;
    private String SKILL_SPECIALIZATION4;
    private Integer BASIC_PAY;
    private Integer DA_RATE;
    private Integer UNIT_RATE_HOUR;
    private Integer OT_UNIT_RATE_HOUR;
    private String NIGHT_ELIGIBLE;
    private String CONTRACTOR_FIRM_NAME;
    private String DEPARTMENT_CODE;
    private String DEPARTMENT_DESC;
    private String DEPARTMENT_GROUP;
    private String MANAGER_ID1;
    private String MANAGER_DEPT_ID1;
    private String MANAGER_ID2;
    private String MANAGER_DEPT_ID2;
    private Integer ENTRY_REQUIRED;
    private String HEIGHT_PASS_AVAILABLE;
    private Date HEIGHT_PASS_EXPIRY_DATE;
    private String SPECIAL_PASS_LICENSE1;
    private Date SPECIAL_PASS_LICENSE1_EXP_DATE;
    private String SPECIAL_PASS_LICENSE2;
    private Date SPECIAL_PASS_LICENSE2_EXP_DATE;
    private String SPECIAL_PASS_LICENSE3;
    private Date SPECIAL_PASS_LICENSE3_EXP_DATE;
    private String ACTIVE_FLAG;
    private String ATTRIBUTE1;
    private String ATTRIBUTE2;
    private String ATTRIBUTE3;
    private String ATTRIBUTE4;
    private String ATTRIBUTE5;
    private String ATTRIBUTE6;
    private String ATTRIBUTE7;
    private String ATTRIBUTE8;
    private String ATTRIBUTE9;
    private String ATTRIBUTE10;
    private String ATTRIBUTE11;
    private String ATTRIBUTE12;
    private String ATTRIBUTE13;
    private String ATTRIBUTE14;
    private String ATTRIBUTE15;
    private String CREATION_DATE;
    private Integer CREATED_BY;
    private Date LAST_UPDATE_DATE;
    private Integer LAST_UPDATED_BY;
    private Date LAST_UPDATE_LOGIN;
    private Integer PERSON_ID;
    private String EMP_STATUS;
    private String RESOURCE_STATUS;
    private String ERR_MESSAGE;
    private Integer REQUEST_ID;

}
