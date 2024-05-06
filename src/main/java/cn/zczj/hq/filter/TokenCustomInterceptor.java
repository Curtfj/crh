package cn.zczj.hq.filter;

import cn.zczj.hq.common.Result;
import cn.zczj.hq.pojo.dto.UserInfoDto;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@WebFilter("/custom/*")
public class TokenCustomInterceptor implements Filter {

    private ObjectMapper objectMapper;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        objectMapper = new ObjectMapper();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String userToken = request.getHeader("Authorization");
        if (userToken == null || userToken.isEmpty()) {
            handleUnauthorized(servletResponse);
            return;
        }
        // 创建 HttpClient 实例
        HttpClient client = HttpClient.newHttpClient();
        // 创建 HttpRequest 对象
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("https://zszcdx.zhoushan.gov.cn/reward/api/dg_sso/auth/userinfo/"))
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
            System.out.println("Status Code: " + statusCode);
            // 获取响应头部信息
            headers = response.headers();
            headers.map().forEach((k, v) -> System.out.println(k + ": " + v));
            // 获取响应体内容
            responseBody = response.body();
        } catch (Exception e) {
            e.printStackTrace();
            handleUnauthorized(servletResponse);
            return;
        }
        // 根据条件判断是否继续处理请求
        if(statusCode == 200){
            JSONObject jsonObject = JSONObject.parseObject(responseBody);
            UserInfoDto userInfoDto = UserInfoDto.getInstance();
            userInfoDto.setId(JSONObject.parseObject(jsonObject.getString("data")).getLong("id"));
            userInfoDto.setUserName(JSONObject.parseObject(jsonObject.getString("data")).getString("real_name"));
            // 请求满足条件，继续处理
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            handleUnauthorized(servletResponse);
        }

    }

    @Override
    public void destroy() {

    }

    private void handleUnauthorized(ServletResponse servletResponse) throws IOException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE + ";charset=" + StandardCharsets.UTF_8);
        Result apiResponse = Result.fail(401, "当前未登录");
        objectMapper.writeValue(response.getWriter(), apiResponse);
    }
}
