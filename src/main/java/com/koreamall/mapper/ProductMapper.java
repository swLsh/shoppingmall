package com.koreamall.mapper;


import com.koreamall.dto.CategoryDTO;
import com.koreamall.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<ProductDTO> selectProductsByCategoryNo(Integer categoryNo);


    ProductDTO selectProductByNo(Integer no);
    /***************** 카테고리 관련 ************/

    List<CategoryDTO> selectCategories();
    CategoryDTO selectCategory(Integer categoryNo);
}
