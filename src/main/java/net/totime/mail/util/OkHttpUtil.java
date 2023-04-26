package net.totime.mail.util;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;

import java.util.Map;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/04/13
 * @description OKHttp工具类
 * @since 1.0.0
 */
public class OkHttpUtil {
    /**
     * 阿里云第三方接口调用
     *
     * @param appCode 应用程序代码
     * @param url     url
     * @param param   参数
     * @return {@link String}
     */
    public static String aliPost(String appCode, String url, Map<String, String> param) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        FormBody.Builder formbuilder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : param.entrySet()) {
            formbuilder.add(entry.getKey(), entry.getValue());
        }
        FormBody formBody = formbuilder.build();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(formBody)
                .addHeader("Authorization", "APPCODE " + appCode)
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
