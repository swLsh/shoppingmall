package com.koreamall.service;

import com.koreamall.dto.CategoryDTO;
import com.koreamall.dto.ProductDTO;
import com.koreamall.dto.user.CartDTO;
import com.koreamall.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductMapper productMapper;

    public List<ProductDTO> get_products(Integer categoryNo) {
        return productMapper.selectProductsByCategoryNo(categoryNo);

    }

    public ProductDTO get_product_by_no(Integer productNo) {
        return productMapper.selectProductByNo(productNo);

    }

    // 장바구니에 존재하는 상품들의 내용을 설정해주는 서비스
    public void set_product_information_of_carts(List<CartDTO> carts){
        carts.forEach(cart -> {
            ProductDTO product = cart.getProduct();
            ProductDTO findProduct = productMapper.selectProductByNo(product.getNo());
            product.setPrice(findProduct.getPrice());
            product.setImages(findProduct.getImages());
            product.setCategory(findProduct.getCategory());
        });
    }

    // 상품들이 가지는 모든 색상 리스트를 가져오는 메서드
    public List<String> get_product_colors(List<ProductDTO> products){
        return products.parallelStream()
                // 하나의 상품의 컬러 값을 , 로 구분해서 따로 나눈다
                .flatMap(product -> Arrays.stream(product.getColors().split(",")))
                .map(color -> {
                    // 만약 하나의 컬러 값이 White / Green / Green 식으로 되어 있다면 White만 가져감
                    if(color.contains("/")){
                        color = color.split("/")[0];
                    }
                    // 공백 제거. 띄어쓰기는 - 로 변경. 대문자 변환.
                    return color.trim().replaceAll(" ", "-").toUpperCase();
                })
                .distinct().toList();
    }

    public List<CategoryDTO> get_categories(){
        return productMapper.selectCategories();
    }

    public CategoryDTO get_category(Integer categoryNo){
        return productMapper.selectCategory(categoryNo);
    }

}
