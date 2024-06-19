package com.washmaintance.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName wash_room
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WashRoom {
    private Integer id;
    @JsonAlias("wName")
    private String wName;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @JsonAlias("wAddress")
    private String wAddress;
    private String pic;
    private Integer usages;
    private String useName;
    private String washRoomName;





}