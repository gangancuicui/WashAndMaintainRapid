package com.washmaintance.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleBean {
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private Integer num;
}
