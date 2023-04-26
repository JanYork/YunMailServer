package net.totime.mail.domain.sponsor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.totime.mail.entity.Sponsor;
import net.totime.mail.entity.User;
import net.totime.mail.service.SponsorService;
import net.totime.mail.service.UserService;
import net.totime.mail.vo.SponsorInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/19
 * @description 赞助服务类
 * @since 1.0.0
 */
@Service
public class SponsorOperateService {
    @Resource
    private SponsorService sponsorService;
    @Resource
    private UserService userService;

    /**
     * 创建赞助订单
     *
     * @param sponsor 赞助信息
     * @return {@link Boolean} 是否创建成功
     */
    public Boolean createSponsor(Sponsor sponsor) {
        return sponsorService.save(sponsor);
    }

    /**
     * 通过ID获取赞助信息
     *
     * @param id 赞助ID
     * @return {@link Sponsor}
     */
    public Sponsor getSponsorById(Long id) {
        return sponsorService.getById(id);
    }

    /**
     * 更新赞助信息
     *
     * @param sponsor 赞助信息
     * @return {@link Boolean} 是否更新成功
     */
    public Boolean updateSponsor(Sponsor sponsor) {
        return sponsorService.updateById(sponsor);
    }

    /**
     * 获取赞助信息-分页
     *
     * @param page 页码
     * @param size 每页数量
     * @return {@link SponsorInfoVO}
     */
    public List<SponsorInfoVO> querySponsor(Integer page, Integer size) {
        return sponsorService.page(
                        new Page<>(page, size),
                        new QueryWrapper<Sponsor>().eq("state", 1).orderByDesc("create_time")
                )
                .getRecords().stream().map(sponsor -> {
                    SponsorInfoVO sponsorVO = new SponsorInfoVO();
                    BeanUtils.copyProperties(sponsor, sponsorVO);
                    User byId = userService.getById(sponsor.getUserId());
                    sponsorVO.setUserNickname(byId.getName());
                    sponsorVO.setUserAvatar(byId.getAvatar());
                    return sponsorVO;
                }).collect(Collectors.toList());
    }
}
