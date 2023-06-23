/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ma.glasnost.orika.MapperFacade;
import net.totime.mail.dto.BlessingToWishDTO;
import net.totime.mail.entity.Blessing;
import net.totime.mail.entity.BlessingToWish;
import net.totime.mail.entity.Wish;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.BlessingService;
import net.totime.mail.service.BlessingToWishService;
import net.totime.mail.service.WishService;
import net.totime.mail.util.CheckReturn;
import net.totime.mail.util.RedisUtil;
import net.totime.mail.vo.WishBlessingVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 祝福语录表(BlessingToWish)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-06-14 22:59:34
 */
@RestController
@RequestMapping("/user/blessing/wish")
@Api(tags = "[用户]心愿祝福信息")
public class BlessingToWishApi {
    @Resource
    private BlessingToWishService service;
    @Resource
    private MapperFacade mapperFacade;
    @Resource
    private RedisUtil rut;
    @Resource
    private WishService wishService;
    @Resource
    private BlessingService blessingService;

    /**
     * 新增祝福
     *
     * @param blessingDTO 祝福信息
     * @return {@link ApiResponse}
     */
    @PostMapping("/insert")
    @ApiOperation("祝福")
    public ApiResponse<Boolean> insert(@RequestBody @Valid BlessingToWishDTO blessingDTO) {
        CheckReturn<BlessingToWish> check = check(blessingDTO);
        if (!check.getStatus()) {
            return ApiResponse.fail(false).message(check.getMsg());
        }
        BlessingToWish blessingToWish = check.getValue();
        blessingToWish.setUserId(StpUtil.getLoginIdAsLong());
        blessingToWish.setCreateTime(new Date());
        blessingToWish.setIsFilter(false);
        boolean save = service.save(blessingToWish);
        return save ? ApiResponse.ok(true).message("谢谢") : ApiResponse.fail(false).message("祝福失败");
    }

    /**
     * 删除祝福
     *
     * @param id 祝福ID
     * @return {@link ApiResponse}
     */
    @PostMapping("/delete")
    @ApiOperation("删除祝福")
    public ApiResponse<Boolean> delete(@RequestParam @Valid @NotNull(message = "祝福ID不能为空") String id) {
        BlessingToWish wish = service.getById(Long.parseLong(id));
        if (wish == null) {
            return ApiResponse.fail(false).message("祝福不存在");
        }
        wish.setIsFilter(false);
        boolean remove = service.updateById(wish);
        return remove ? ApiResponse.ok(true).message("删除成功") : ApiResponse.fail(false).message("删除失败");
    }

    /**
     * 查询祝福
     *
     * @param wishId 心愿ID
     * @param page   页码
     * @param size   每页数量
     * @return {@link ApiResponse}
     */
    @GetMapping("/list")
    @ApiOperation("查询祝福")
    public ApiResponse<List<WishBlessingVO>> list(@RequestParam @Valid @NotNull(message = "心愿ID不能为空") String wishId,
                                                  @RequestParam(defaultValue = "1") @Valid @NotNull(message = "页码不能为空") Integer page,
                                                  @RequestParam(defaultValue = "10") @Valid @NotNull(message = "每页数量不能为空") Integer size) {
        List<WishBlessingVO> collect = service.page(
                new Page<>(page, size),
                new LambdaQueryWrapper<BlessingToWish>()
                        .eq(BlessingToWish::getWishId, wishId)
                        .eq(BlessingToWish::getIsFilter, false)
                        .orderByDesc(BlessingToWish::getCreateTime)
        ).getRecords().stream().parallel().filter(Objects::nonNull).map(w -> {
            WishBlessingVO vo = mapperFacade.map(w, WishBlessingVO.class);
            Blessing blessing = blessingService.getById(w.getBlessing());
            vo.setBlessingText(blessing.getContent());
            return vo;
        }).collect(Collectors.toList());
        return ApiResponse.ok(collect);
    }

    /**
     * 校验
     *
     * @param blessingDTO 评论信息
     */
    private CheckReturn<BlessingToWish> check(BlessingToWishDTO blessingDTO) {
        // 检查语录是否存在
        Blessing blessing = blessingService.getById(blessingDTO.getBlessing());
        if (blessing == null) {
            return CheckReturn.fail("祝福语不存在");
        }
        // 检查心愿是否存在
        Wish wish = wishService.getById(blessingDTO.getWishId());
        if (wish == null) {
            return CheckReturn.fail("心愿不存在");
        }
        // 获取用户ID
        long userId = StpUtil.getLoginIdAsLong();
        // 一分钟内容防重
        Long incr = rut.incr("blessing:" + userId + blessingDTO.getWishId(), 60L);
        if (incr > 1) {
            return CheckReturn.fail("1分钟冷却期间不能提交");
        }
        return CheckReturn.ok(mapperFacade.map(blessingDTO, BlessingToWish.class));
    }
}
