package com.maxbyte.sam.OracleDBFlow.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class MasterFNDUser {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "USER_ID")
    private Integer userId;
    private String USER_NAME;
    private String DESCRIPTION;
    private String EMAIL_ADDRESS;
    private LocalDateTime END_DATE;


}
