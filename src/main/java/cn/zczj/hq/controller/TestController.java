package cn.zczj.hq.controller;

import cn.zczj.hq.pojo.dto.AdminUserInfoDto;
import com.alibaba.fastjson.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestController {
    public static void main(String[] args) {
        //调用第三方接口
        HttpClient client = HttpClient.newHttpClient();
        // 创建 HttpRequest 对象
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://zqd.zseco.zhoushan.gov.cn/reward/mg/dg_admin/v2/user_info/"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJsb2dpbklkIjoiMjExMjAyIiwiZXhwIjoxNzE0MTEyNDM5fQ.YrJMJ6Jv0qq7dLMc0SPZGhYCxTxo_UEmuZS_TKoOz7U")
                .build();
        int statusCode = 0;
        HttpHeaders headers = null;
        String responseBody = "";
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // 获取响应状态码
            statusCode = response.statusCode();
            System.out.println("Status Code: " + statusCode);

            // 获取响应头部信息
            headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ": " + v));

            // 获取响应体内容
            responseBody = response.body();
            System.out.println("Response Body: " + responseBody);
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            AdminUserInfoDto adminInstance = AdminUserInfoDto.getAdminInstance();
            adminInstance.setId(JSONObject.parseObject(jsonObject.getString("data")).getLong("id"));
            adminInstance.setUserName(JSONObject.parseObject(jsonObject.getString("data")).getString("real_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
