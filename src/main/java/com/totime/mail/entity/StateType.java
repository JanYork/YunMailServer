package com.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (StateType)表实体类
 *
 * @author JanYork
 * @since 2023-03-26 17:53:59
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StateType extends Model<StateType> {
    /**
     * 状态类型ID
     */
    private Integer id;
    /**
     * 状态名称
     */
    private String state;
}