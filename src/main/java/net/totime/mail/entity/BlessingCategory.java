package net.totime.mail.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 祝福分类表(BlessingCategory)表实体类
 *
 * @author JanYork
 * @since 2023-03-27 22:13:32
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BlessingCategory extends Model<BlessingCategory> {
    /**
     * 祝福分类ID
     */
    private Integer id;
    /**
     * 祝福分类名称
     */
    private String name;
}