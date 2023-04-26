package net.totime.mail.controller.mail;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.domain.mail.MailOperateService;
import net.totime.mail.dto.MailDTO;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.vo.MailVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/28
 * @description 云寄邮件服务接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/mail")
@Api(tags = "云寄邮件服务接口")
@ApiSupport(author = "JanYork")
public class MailApi {
    @Resource
    private MailOperateService mos;
    private static final Integer SIZE = 8;

    /**
     * 分页查询邮件
     *
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link MailVO}>> 邮件列表
     */
    @GetMapping("/query/{page}")
    @ApiOperation(value = "分页查询邮件", notes = "分页以8条/页为单位")
    public ApiResponse<List<MailVO>> queryMail(@PathVariable Integer page) {
        return ApiResponse.ok(mos.queryMail(page, SIZE));
    }

    /**
     * 分页查询当前时间之后的邮件
     *
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link MailVO}>> 邮件列表
     */
    @GetMapping("/queryAfterNow/{page}")
    @ApiOperation(value = "查询当前时间之后的邮件", notes = "分页以8条/页为单位")
    public ApiResponse<List<MailVO>> queryMailAfterNow(@PathVariable Integer page) {
        return ApiResponse.ok(mos.queryMailAfterNow(page, SIZE));
    }

    /**
     * 分页查询当前时间之前的邮件
     *
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link MailVO}>> 邮件列表
     */
    @GetMapping("/queryBeforeNow/{page}")
    @ApiOperation(value = "查询当前时间之前的邮件", notes = "分页以8条/页为单位")
    public ApiResponse<List<MailVO>> queryMailBeforeNow(@PathVariable Integer page) {
        return ApiResponse.ok(mos.queryMailBeforeNow(page, SIZE));
    }

    /**
     * 根据邮件ID查询邮件
     *
     * @param id 邮件id
     * @return {@link ApiResponse}<{@link MailVO}>  邮件详情
     */
    @RequestMapping("/queryById")
    @ApiOperation(value = "根据邮件ID查询邮件", notes = "邮件ID与信件ID均唯一")
    public ApiResponse<MailVO> queryMailDetail(@RequestParam("id") String id) {
        return ApiResponse.ok(mos.queryMailById(id));
    }

    /**
     * 根据用户ID查询邮件
     *
     * @param id   用户id
     * @param page 页面
     * @return {@link ApiResponse}<{@link List}<{@link MailVO}>> 邮件列表
     */
    @RequestMapping("/queryByUserId/{page}")
    @ApiOperation(value = "根据用户ID查询邮件", notes = "分页以8条/页为单位")
    public ApiResponse<List<MailVO>> queryMailByUserId(@RequestParam("id") Long id, @PathVariable Integer page) {
        return ApiResponse.ok(mos.queryMailByUserId(id, page, SIZE));
    }

    /**
     * 根据发往邮箱查询邮件
     *
     * @param mail 发往邮箱
     * @param page 页面
     * @return {@link ApiResponse}<{@link List}<{@link MailVO}>> 邮件列表
     */
    @RequestMapping("/queryByGoToMail/{page}")
    @ApiOperation(value = "根据发往邮箱查询邮件", notes = "分页以8条/页为单位")
    public ApiResponse<List<MailVO>> queryMailByGoToMail(@RequestParam("mail") String mail, @PathVariable Integer page) {
        return ApiResponse.ok(mos.queryMailByGoToMail(mail, page, SIZE));
    }

    /**
     * 分页查询允许公开展示的邮件
     *
     * @param page 当前页面
     * @return {@link ApiResponse}<{@link List}<{@link MailVO}>> 邮件列表
     */
    @GetMapping("/queryPublic/{page}")
    @ApiOperation(value = "分页查询允许公开展示的邮件", notes = "分页以8条/页为单位")
    public ApiResponse<List<MailVO>> queryPublicMail(@PathVariable Integer page) {
        return ApiResponse.ok(mos.queryMailByPublic(page, SIZE));
    }

    /**
     * 新增邮件(自动绑定用户)
     *
     * @param mailDTO 邮件信息
     * @return {@link ApiResponse}<{@link Boolean}> 是否成功
     */
    @PostMapping("/add")
    @SaCheckLogin
    @ApiOperation(value = "新增邮件", notes = "此接口会自动绑定用户，强制要求登录")
    public ApiResponse<Boolean> addMail(@Valid @RequestBody MailDTO mailDTO) {
        return ApiResponse.ok(mos.addMail(mailDTO));
    }
}
