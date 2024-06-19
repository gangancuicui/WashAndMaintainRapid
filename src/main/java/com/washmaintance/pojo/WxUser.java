package com.washmaintance.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @TableName wx_user
 */
@TableName(value ="wx_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxUser implements Serializable {
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
    private String nickName;

    /**
     * 
     */
    private String avatarUrl;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String tel;
    private LocalDateTime createTime;

    /**
     *
     */
    private LocalDateTime updateTime;
    private String code;
    private String wxToken;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;


}