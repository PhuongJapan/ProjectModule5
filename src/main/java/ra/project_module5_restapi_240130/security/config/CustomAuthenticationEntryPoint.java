package ra.project_module5_restapi_240130.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
//giup ghi lock ra server
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // trả về thông điệp lỗi
        log.error("err",authException.getMessage());
        response.setStatus(403);
        response.setHeader("error","Forbiden");
        Map<String ,String> map = new HashMap<>();
        map.put("message","Bạn không có quyền truy cập");
        new ObjectMapper().writeValue(response.getOutputStream(),map);
    }
}
