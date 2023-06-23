/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import net.totime.mail.entity.Blessing;
import net.totime.mail.entity.BlessingCategory;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.BlessingCategoryService;
import net.totime.mail.service.BlessingService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 愿望祝福表(Blessing)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-06-14 22:59:31
 */
@RestController
@RequestMapping("/common/blessing")
@Api(tags = "[公共]祝福语录信息")
@Validated
public class BlessingApi {
    @Resource
    private BlessingService blessingService;
    @Resource
    private BlessingCategoryService blessingCategoryService;

    /**
     * 获取祝福语录(根据分类)
     *
     * @param categoryId 分类ID
     * @return {@link ApiResponse}<{@link Blessing}>
     */
    @GetMapping("/get/{categoryId}")
    public ApiResponse<List<Blessing>> getBlessingByCategory(@PathVariable @Valid @NotNull(message = "分类ID不能为空") String categoryId) {
        BlessingCategory category = blessingCategoryService.getById(categoryId);
        if (category == null) {
            return ApiResponse.<List<Blessing>>ok(null).message("分类不存在");
        }
        List<Blessing> list = blessingService.list(
                new LambdaQueryWrapper<>(Blessing.class)
                        .eq(Blessing::getCategoryId, categoryId)
        );
        if (list.isEmpty()) {
            return ApiResponse.<List<Blessing>>ok(null).message("暂无数据");
        }
        return ApiResponse.ok(list).message("获取成功");
    }
}
