/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.totime.mail.entity.Admin;
import org.apache.ibatis.annotations.Mapper;

/**
 * 管理员信息表(Admin)表数据层
 *
 * @author JanYork
 * @since 2023-06-14 22:59:31
 */
@Mapper
public interface AdminMapper extends BaseMapper<Admin> {

}
