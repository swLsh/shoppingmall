package com.koreamall.mapper;


import com.koreamall.dto.user.CartDTO;
import com.koreamall.dto.user.OrderDTO;
import com.koreamall.dto.user.SocialUserDTO;
import com.koreamall.dto.user.UserDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    UserDTO selectUserById(String id);
    UserDTO selectUserByCi(String ci);
    /************** 회원가입 **************/
    void insert_user(UserDTO user); // 유저 회원가입
    /******************** SocialUser ************************/
    SocialUserDTO selectSocialUserById(String id);
    void insert_socialUser(SocialUserDTO socialUser); // 소셜 유저 회원가입 (기존 유저와 연결)
    /********************* 장바구니 **************************/
    List<CartDTO> selectCartsByUserId(String userId);
    CartDTO selectCartDuplicated(CartDTO cart); // 장바구니에 해당 상품이 존재?
    void insertCart(CartDTO cart); // 장바구니에 상품 추가
    void updateCartAmount(
            @Param("cartNo") Integer cartNo,
            @Param("amount") Integer amount
    ); // 장바구니에 존재하는 상품의 수량 변경
    void deleteCart(List<CartDTO> carts); // 해당 장바구니의 상품을 삭제

    void insertOrder(OrderDTO order);
    void insertOrderProducts(OrderDTO order); // 위 주문에 해당하는 상품 정보 등록

}