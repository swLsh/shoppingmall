package com.koreamall.interceptor;

import com.koreamall.dto.CategoryDTO;
import com.koreamall.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
@Component
public class CategoryInterceptor implements HandlerInterceptor {
   @Autowired private ProductService productService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // header 에 표시할 모든 카테고리 리스트
        List<CategoryDTO> categories = productService.get_categories();
        request.setAttribute("categories", categories);
        return true;
    }
}
