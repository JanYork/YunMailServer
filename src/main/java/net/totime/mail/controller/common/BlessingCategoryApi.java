/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.common;

import io.swagger.annotations.Api;
import net.totime.mail.entity.BlessingCategory;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.BlessingCategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 祝福分类表(BlessingCategory)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-06-14 22:59:33
 */
@RestController
@RequestMapping("/common/blessing/category")
@Api(tags = "[公共]祝福分类信息")
public class BlessingCategoryApi {
    @Resource
    private BlessingCategoryService blessingCategoryService;

    /**
     * 获取分类信息
     *
     * @return {@link ApiResponse}<{@link List}<{@link BlessingCategory}>>
     */
    @GetMapping("/list")
    public ApiResponse<List<BlessingCategory>> list() {
        return ApiResponse.ok(blessingCategoryService.list());
    }
}
