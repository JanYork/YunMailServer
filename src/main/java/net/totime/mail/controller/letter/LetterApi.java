/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.letter;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.domain.letter.LetterOperateService;
import net.totime.mail.dto.LetterDTO;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.vo.LetterVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Letter)表接口层
 *
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/22
 * @description 描述
 * @since 2023-03-26 17:54:03
 */
@RestController
@RequestMapping("/api/letter")
@Api(tags = "云寄信件服务接口")
@ApiSupport(author = "JanYork")
public class LetterApi {
    @Resource
    private LetterOperateService los;
    private static final Integer SIZE = 8;

    /**
     * 分页查询信件
     *
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link LetterVO}>> 信件列表
     */
    @GetMapping("/query/{page}")
    @ApiOperation(value = "分页查询信件", notes = "分页以8条/页为单位")
    public ApiResponse<List<LetterVO>> queryLetter(@PathVariable Integer page) {
        return ApiResponse.ok(los.queryLetter(page, SIZE));
    }

    /**
     * 分页查询当前时间之后的信件
     *
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link LetterVO}>> 信件列表
     */
    @GetMapping("/queryAfterNow/{page}")
    @ApiOperation(value = "查询当前时间之后的信件", notes = "分页以8条/页为单位")
    public ApiResponse<List<LetterVO>> queryLetterAfterNow(@PathVariable Integer page) {
        return ApiResponse.ok(los.queryLetterAfterNow(page, SIZE));
    }

    /**
     * 分页查询当前时间之前的信件
     *
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link LetterVO}>> 信件列表
     */
    @GetMapping("/queryBeforeNow/{page}")
    @ApiOperation(value = "查询当前时间之前的信件", notes = "分页以8条/页为单位")
    public ApiResponse<List<LetterVO>> queryLetterBeforeNow(@PathVariable Integer page) {
        return ApiResponse.ok(los.queryLetterBeforeNow(page, SIZE));
    }

    /**
     * 根据id查询信件
     *
     * @param id 信件id
     * @return {@link ApiResponse}<{@link LetterVO}> 信件
     */
    @GetMapping("/queryById")
    @ApiOperation(value = "根据id查询信件", notes = "邮件ID与信件ID均唯一")
    public ApiResponse<LetterVO> queryLetterById(Long id) {
        return ApiResponse.ok(los.queryLetterById(id));
    }

    /**
     * 根据用户id查询信件
     *
     * @param id   id
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link LetterVO}>> 信件列表
     */
    @GetMapping("/queryByUserId/{page}")
    @ApiOperation(value = "根据用户id查询信件", notes = "分页以8条/页为单位")
    public ApiResponse<List<LetterVO>> queryLetterByUserId(@RequestParam("id") Long id, @PathVariable Integer page) {
        return ApiResponse.ok(los.queryLetterByUserId(id, page, SIZE));
    }

    /**
     * 通过收件人手机号查询信件
     *
     * @param phone 手机号
     * @param page  当前页面
     * @return {@link ApiResponse}<{@link List}<{@link LetterVO}>> 信件列表
     */
    @GetMapping("/queryByToEmail/{page}")
    @ApiOperation(value = "根据收件人手机号查询邮件", notes = "分页以8条/页为单位")
    public ApiResponse<List<LetterVO>> queryLetterByPhone(@RequestParam("phone") String phone, @PathVariable Integer page) {
        return ApiResponse.ok(los.queryLetterByPhone(phone, page, SIZE));
    }

    /**
     * 分页查询允许公开展示的信件
     *
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link LetterVO}>> 信件列表
     */
    @GetMapping("/queryPublic/{page}")
    @ApiOperation(value = "分页查询允许公开展示的邮件", notes = "分页以8条/页为单位")
    public ApiResponse<List<LetterVO>> queryAllowPublic(@PathVariable Integer page) {
        return ApiResponse.ok(los.queryAllowPublic(page, SIZE));
    }

    /**
     * 新增信件(自动绑定用户)
     *
     * @param letterDTO 信件信息
     * @return {@link ApiResponse}<{@link Boolean}> 是否成功
     */
    @PostMapping("/add")
    @SaCheckLogin
    @ApiOperation(value = "新增信件", notes = "此接口会自动绑定用户，强制要求登录")
    public ApiResponse<Boolean> addLetter(@RequestBody LetterDTO letterDTO) {
        return ApiResponse.ok(los.addLetter(letterDTO));
    }
}
