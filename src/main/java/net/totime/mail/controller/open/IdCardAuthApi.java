package net.totime.mail.controller.open;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import net.totime.mail.annotation.RateLimiter;
import net.totime.mail.dto.IdCardAuthDTO;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.util.OkHttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/13
 * @description 实名认证接口
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/open/")
public class IdCardAuthApi {
    @Value("${third.three-element.url}")
    private String threeElementUrl;
    @Value("${third.two-element.url}")
    private String twoElementUrl;
    @Value("${third.app-code}")
    private String appcode;

    /**
     * 实名认证-三要素
     *
     * @param cardInfo 实名认证要素
     * @return {@link ApiResponse} 实名认证结果
     */
    @GetMapping("/idCard/auth")
    @RateLimiter(count = 3, time = 60 * 60 * 24 * 7)
    public ApiResponse<String> idCardAuth(@RequestBody IdCardAuthDTO cardInfo) {
        Map<String, String> param = new HashMap<>(16);
        param.put("idcard", cardInfo.getIdCard());
        param.put("mobile", cardInfo.getPhone());
        param.put("name", cardInfo.getName());
        String response = OkHttpUtil.aliPost(appcode, threeElementUrl, param);
        if (StringUtils.isEmpty(response)) {
            return ApiResponse.fail("认证异常");
        }
        HashMap<String, Object> result = JSONObject.parseObject(response, new TypeReference<HashMap<String, Object>>() {
        });
        if (!"0".equals(result.get("code"))) {
            return ApiResponse.fail("认证异常");
        }
        HashMap<String, String> res = JSONObject.parseObject(JSONObject.toJSONString(result.get("result")), new TypeReference<HashMap<String, Object>>() {
        });
        String authCode = res.get("res");
        return "1".equals(authCode) ? ApiResponse.ok("认证成功") : ApiResponse.fail("认证失败");
    }

    /**
     * 实名认证-二要素
     *
     * @param idCard 身份证
     * @param name   姓名
     * @return {@link ApiResponse}<{@link String}> 实名认证结果
     */
    @GetMapping("/idCard/auth2")
    @RateLimiter(count = 3, time = 60 * 60 * 24 * 7)
    public ApiResponse<String> idCardAuth2(@RequestParam String idCard, @RequestParam String name) {
        Map<String, String> param = new HashMap<>(16);
        param.put("cardNo", idCard);
        param.put("realName", name);
        String response = OkHttpUtil.aliPost(appcode, twoElementUrl, param);
        if (StringUtils.isEmpty(response)) {
            return ApiResponse.fail("认证异常");
        }
        HashMap<String, Object> result = JSONObject.parseObject(response, new TypeReference<HashMap<String, Object>>() {
        });
        if (0 != (int) result.get("error_code")) {
            return ApiResponse.fail("认证异常");
        }
        HashMap<String, Object> res = JSONObject.parseObject(JSONObject.toJSONString(result.get("result")), new TypeReference<HashMap<String, Object>>() {
        });
        return (Boolean) res.get("isok") ? ApiResponse.ok("认证成功") : ApiResponse.fail("认证失败");
    }
}
