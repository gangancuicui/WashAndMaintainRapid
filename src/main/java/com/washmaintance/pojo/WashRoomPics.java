package com.washmaintance.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

/**
 * 
 * @TableName wash_room_pics
 */
@TableName(value ="wash_room_pics")
@Data
public class WashRoomPics implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String pic;

    /**
     * 
     */
    private Integer washRoomId;

    private LocalDateTime createTime;

    /**
     *
     */
    private LocalDateTime updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        WashRoomPics other = (WashRoomPics) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPic() == null ? other.getPic() == null : this.getPic().equals(other.getPic()))
            && (this.getWashRoomId() == null ? other.getWashRoomId() == null : this.getWashRoomId().equals(other.getWashRoomId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPic() == null) ? 0 : getPic().hashCode());
        result = prime * result + ((getWashRoomId() == null) ? 0 : getWashRoomId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", pic=").append(pic);
        sb.append(", washRoomId=").append(washRoomId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }


}