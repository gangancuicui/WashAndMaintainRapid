package com.washmaintance.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * 
 * @TableName wash_date
 */
@TableName(value ="wash_date")
@Data
public class WashDate implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @JsonAlias("id")
    private Integer id;

    /**
     * 
     */
    @JsonAlias("wDate")
    private LocalDate wDate;

    /**
     * 
     */
    @JsonAlias("wStart")
    private LocalTime wStart;

    /**
     * 
     */
    @JsonAlias("wEnd")
    private LocalTime wEnd;

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
    @JsonAlias("num")
    private Integer num;

    /**
     * 
     */
    @JsonAlias("wRoom")
    private Integer wRoom;

    @JsonAlias("washRoomName")
    private String washRoomName;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", wDate=").append(wDate);
        sb.append(", wStart=").append(wStart);
        sb.append(", wEnd=").append(wEnd);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", num=").append(num);
        sb.append(", wRoom=").append(wRoom);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


}