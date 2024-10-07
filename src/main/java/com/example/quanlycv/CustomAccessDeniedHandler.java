package com.example.quanlycv;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        // Thông báo lỗi
        request.getSession().setAttribute("errorMessage", "Bạn không có quyền truy cập vào trang này.");

        // Chuyển hướng đến trang thông báo
        response.sendRedirect(request.getContextPath() + "/access-denied");
    }
}

