package com.maxbyte.sam.SecondaryDBFlow.Response;

import lombok.Data;

import java.util.List;

@Data
public class IsNotResponse {
    private String rcaNumber;
    private IsNotCategory whoList;
    private IsNotCategory whatList;
    private IsNotCategory whyList;
    private IsNotCategory whereList;
    private IsNotCategory whenList;
    private IsNotCategory howmuchList;
    private IsNotCategory howoftenList;
}
