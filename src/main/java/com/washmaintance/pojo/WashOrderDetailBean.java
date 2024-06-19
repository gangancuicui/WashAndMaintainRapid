package com.washmaintance.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WashOrderDetailBean extends WashOrder{
    private String pic;
    private String washRoomName;
}
