package com.koreamall.dto.user;


import com.koreamall.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CartDTO {
    private Integer no;
    private UserDTO user;
    private ProductDTO product;
    private Integer amount;
    private String size;
    private String color;
}
