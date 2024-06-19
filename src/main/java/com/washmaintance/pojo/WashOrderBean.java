package com.washmaintance.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WashOrderBean extends WashOrder{
    private String WashName;
    private String pic;
}
