package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 状态类型表(StateType)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:35
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