package cn.totime.api.auth;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.xkcoding.justauth.AuthRequestFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author JanYork
 * @date 2023/1/13 19:27
 * @description 授权登录接口
 */
@RestController
@RequestMapping("/oauth")
@Api(tags = "授权登录接口")
@ApiSupport(author = "JanYork")
public class AuthController {
    @Autowired
    private AuthRequestFactory factory;

    /**
     * 获取授权信息列表
     *
     * @return 授权信息列表
     */
    @GetMapping
    public List<String> list() {
        return factory.oauthList();
    }

    /**
     * 获取授权登录页面
     *
     * @param type     授权类型，参考枚举{@link cn.totime.common.auth.AuthType}
     * @param response 响应
     * @throws IOException IO异常
     */
    @GetMapping("/login/{type}")
    @ApiOperation(value = "获取授权", notes = "获取授权登录页面")
    public void login(@PathVariable("type") String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
    }

    /**
     * 授权回调
     *
     * @param type     授权类型，参考枚举{@link cn.totime.common.auth.AuthType}
     * @param callback 回调参数
     * @return 授权结果
     */
    @GetMapping("/{type}/callback")
    @ApiOperation(value = "授权回调", notes = "授权回调参数获取")
    public AuthResponse login(@PathVariable("type") String type, AuthCallback callback) {
        AuthRequest authRequest = factory.get(type);
        return authRequest.login(callback);
    }
}