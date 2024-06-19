package com.washmaintance.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReserveNum {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer washRoom;
    private Integer count;
}
