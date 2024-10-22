package com.maxbyte.sam.SecondaryDBFlow.FNDUser.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class FNDUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer id;
    @Column(name = "USER_ID")
    private Integer userId;
    private String userName;
    private String DESCRIPTION;
    private String EMAIL_ADDRESS;
    private LocalDateTime END_DATE;
}
