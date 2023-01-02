package cn.totime.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * (LetterType)表实体类
 *
 * @author JanYork
 * @since 2023-01-01 20:40:45
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LetterType extends Model<LetterType> {
    private static final long serialVersionUID = 900010417632620951L;

    /**
     * 信件类型自增ID
     */
    @TableId(type = IdType.AUTO)
    private Integer letterTypeId;
    /**
     * 信件类型名称
     */
    private String letterTypeName;
}