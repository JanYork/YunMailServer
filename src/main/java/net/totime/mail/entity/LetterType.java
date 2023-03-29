package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 信件类型表(LetterType)表实体类
 *
 * @author JanYork
 * @since 2023-03-29 10:46:48
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LetterType extends Model<LetterType> {
    private static final long serialVersionUID = 4773048251137248174L;
    /**
     * 信件类型ID
     */
    private Integer id;
    /**
     * 信件类型名称
     */
    private String name;
}