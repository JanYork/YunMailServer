package net.totime.mail.controller;

import com.alibaba.fastjson2.JSON;
import com.raycloud.open.sdk.api.base.exception.ApiRuleException;
import com.raycloud.open.sdk.api.request.KdzsLogisticsTraceSearchRequest;
import com.raycloud.open.sdk.api.response.KdzsLogisticsTraceSearchResponse;
import lombok.SneakyThrows;
import net.totime.mail.domain.courier.CourierOperateService;
import net.totime.mail.dto.CourierCallBackDTO;
import net.totime.mail.mongo.MongoUtil;
import net.totime.mail.response.ApiResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
public class Testc {
    @Resource
    private CourierOperateService courierOperate;
    @Resource
    private MongoUtil mongoUtil;

    @GetMapping("/test")
    public ApiResponse<KdzsLogisticsTraceSearchResponse.LogisticsTraceBean> test() throws ApiRuleException {
        KdzsLogisticsTraceSearchRequest request = new KdzsLogisticsTraceSearchRequest();
        request.setMailNo("JT3031691704847");
        request.setCpCode("JT");
        KdzsLogisticsTraceSearchResponse.LogisticsTraceBean response = courierOperate.checkCourierTrack(request);
        if (response == null) {
            return ApiResponse.fail(null);
        }
        return ApiResponse.ok(response);
    }

    @SneakyThrows
    @PostMapping("/test2")
    public HashMap<String, Object> test2(HttpServletRequest request) {
        String s = courierOperate.checkSign(request);
        if (!StringUtils.hasText(s)) {
            return new HashMap<>(8) {
                private static final long serialVersionUID = -1678563287372612414L;

                {
                    put("code", 100);
                    put("msg", "接收失败");
                }
            };
        }
        CourierCallBackDTO courierCallBack = JSON.parseObject(s, CourierCallBackDTO.class);
        //存入mongo

        return new HashMap<>(8) {

            private static final long serialVersionUID = -5980362810437982932L;

            {
                put("code", 200);
            }
        };
    }
}
