package com.washmaintance.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.washmaintance.utils.LocalTimeDeserializer;
import lombok.Data;

/**
 * 
 * @TableName wash_order
 */
@TableName(value ="wash_order")
@Data
public class WashOrder implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String openid;

    /**
     * 
     */
    private LocalDate washDate;

    /**
     * 
     */
    private Integer washRoom;

    /**
     * 
     */
    private LocalDateTime createTime;

    /**
     * 
     */
    private LocalDateTime updateTime;

    /**
     * 
     */
    private String washId;

    /**
     * 1待核销2已核销3已取消4超时
     */
    private Integer washStatus;

    /**
     * 
     */
    private String washTel;

    private String washRoomName;

    /**
     * 
     */
    private String washName;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime wStart;
    @JsonDeserialize(using = LocalTimeDeserializer.class)
    private LocalTime wEnd;



    @TableField(exist = false)
    private static final long serialVersionUID = 1L;




}