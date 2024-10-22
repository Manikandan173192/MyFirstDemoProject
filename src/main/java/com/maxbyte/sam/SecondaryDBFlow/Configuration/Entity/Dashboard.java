package com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity;

import lombok.Data;

@Data
public class Dashboard {

    private String module;
    private Integer totalCount;
    private Integer open;
    private Integer inProgress;
    private Integer completed;
    private Integer pending;
    private Integer revertBack;
    private Integer closed;
    public Dashboard(String name, Integer totalCount, Integer open,Integer inProgress,Integer completed,Integer pending,Integer revertBack,Integer closed) {
        this.module = name;
        this.totalCount = totalCount;
        this.open = open;
        this.inProgress = inProgress;
        this.completed = completed;
        this.pending = pending;
        this.revertBack = revertBack;
        this.closed = closed;
    }
}
