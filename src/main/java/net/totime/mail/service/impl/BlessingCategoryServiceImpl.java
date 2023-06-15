/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.totime.mail.entity.BlessingCategory;
import net.totime.mail.mapper.BlessingCategoryMapper;
import net.totime.mail.service.BlessingCategoryService;
import org.springframework.stereotype.Service;

/**
 * 祝福分类表(BlessingCategory)表服务实现类
 *
 * @author JanYork
 * @since 2023-06-14 22:59:34
 */
@Service
public class BlessingCategoryServiceImpl extends ServiceImpl<BlessingCategoryMapper, BlessingCategory> implements BlessingCategoryService {

}
