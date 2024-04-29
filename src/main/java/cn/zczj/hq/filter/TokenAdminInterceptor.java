package cn.zczj.hq.filter;

import cn.zczj.hq.pojo.dto.AdminUserInfoDto;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

//@WebFilter("/admin/*")
public class TokenAdminInterceptor implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userToken = request.getHeader("Authorization");
        //todo 通过userToken获取用户信息若获取到则登录成功，反之提示前端跳转到登录界面
        //调用第三方接口
        HttpClient client = HttpClient.newHttpClient();
        // 创建 HttpRequest 对象
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://zqd.zseco.zhoushan.gov.cn/reward/mg/dg_admin/v2/user_info/"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .header("Authorization",userToken)
                .build();
        int statusCode = 0;
        HttpHeaders headers = null;
        String responseBody = "";
        try {
            HttpResponse<String> response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            // 获取响应状态码
            statusCode = response.statusCode();
            // 获取响应头部信息
            headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ": " + v));
            // 获取响应体内容
            responseBody = response.body();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 根据条件判断是否继续处理请求
        if(statusCode == 200){
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            AdminUserInfoDto adminInstance = AdminUserInfoDto.getAdminInstance();
            adminInstance.setId(JSONObject.parseObject(jsonObject.getString("data")).getLong("id"));
            adminInstance.setUserName(JSONObject.parseObject(jsonObject.getString("data")).getString("real_name"));
            // 请求满足条件，继续处理
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
            httpResponse.sendError(401,"当前未登录！");
            filterChain.doFilter(servletRequest,httpResponse);
        }

    }

    @Override
    public void destroy() {
        // 销毁过滤器
    }
}
