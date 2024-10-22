package com.maxbyte.sam.SecondaryDBFlow.Helper;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;


public class DateFilterUtils {
    private Integer mode;
    private LocalDateTime startDate;
    private LocalDateTime endDate = LocalDateTime.now();

    public DateFilterUtils(Integer mode) {
        this.mode = mode;
        this.processDate();
    }
    public DateFilterUtils(Integer mode, LocalDateTime startDate, LocalDateTime endDate) {
        this.mode = mode;
        if(mode==null){
            this.mode=1;
            this.processDate();
        }
        else if(mode==5) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
        else{
            this.processDate();
        }
    }

    public Integer getMode() {
        return mode;
    }
    public LocalDateTime getStartDate() {
        return startDate;
    }
    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void processDate(){
            switch(mode) {
                case 2:
                    this.startDate =this.endDate;
                    this.startDate = this.startDate.withHour(0).withMinute(0).withSecond(0).withNano(0).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
                    break;
                case 3:
                    this.startDate =this.endDate;
                    this.startDate =this.startDate.withHour(0).withMinute(0).withSecond(0).withNano(0).withDayOfMonth(1);
                    break;
                case 4:
                    this.startDate =this.endDate;
                    this.startDate = this.startDate.withHour(0).withMinute(0).withSecond(0).withNano(0).withDayOfYear(1);
                    break;
                default:
                    this.startDate =this.endDate;
                    this.startDate = this.startDate.withHour(0).withMinute(0).withSecond(0).withNano(0);
                    break;
            }
    }
}