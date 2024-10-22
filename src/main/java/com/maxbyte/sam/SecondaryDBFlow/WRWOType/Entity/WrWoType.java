package com.maxbyte.sam.SecondaryDBFlow.WRWOType.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class WrWoType {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;
 private String NAME;
 private String LOOKUP_TYPE;
 @Column(name = "LOOKUP_CODE")
 private Integer lookUpCode;
 private String MEANING;
 private String DESCRIPTION;



}
