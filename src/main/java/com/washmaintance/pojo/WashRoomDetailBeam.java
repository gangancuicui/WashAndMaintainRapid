package com.washmaintance.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WashRoomDetailBeam extends WashRoom{
    private List<WashRoomPics> pics;
}
