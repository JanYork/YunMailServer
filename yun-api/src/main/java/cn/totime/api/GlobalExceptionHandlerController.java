package cn.totime.api;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author JanYork
 * @date 2023/1/14 15:30
 * @description 全局异常处理(增强Controller)
 */
@ControllerAdvice
@Api(value = "全局异常处理")
@ApiSupport(author = "JanYork")
public class GlobalExceptionHandlerController {
    /**
     * 处理TencentCloudSDKException异常{@link TencentCloudSDKException}
     *
     * @param e TencentCloudSDKException
     * @return String
     */
    @ExceptionHandler(TencentCloudSDKException.class)
    @ApiOperation(value = "腾讯云SDK相关异常处理", notes = "处理TencentCloudSDKException异常")
    public String tencentCloudSdkException(TencentCloudSDKException e) {
        return e.getMessage();
    }
}