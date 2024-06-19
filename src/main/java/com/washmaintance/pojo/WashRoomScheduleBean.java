package com.washmaintance.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WashRoomScheduleBean {
    private LocalDate date;
    private List<WashDate> wSchedule;
}
