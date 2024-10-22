package com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity;

import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.AssetGroup;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Organization;
import com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private Integer userId;
    private String empCode;
    private String userEmail;
    private String userPassword;
   //private Integer userPhoneNumber;
    private String userPhoneNumber;
    private String department;
    private Integer departmentId;
    private String role;
    private Integer roleId;
    private Integer organizationId;
    private String organizationCode;
    private String plantName;
    private UserType userType;

    private boolean isActive;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedOn")
    private LocalDateTime updatedOn;

}
