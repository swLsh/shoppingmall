package com.koreamall.dto.user;


import com.koreamall.dto.ProductDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderDTO {
    private String id;
    private UserDTO user;
    private List<ProductDTO> product;
    private String receiverName;
    private String receiverPhone;
    private String receiverPostcode;
    private String receiverAddress;
    private String memo;
    private String impUid;
    private String payMethod;
    private Integer paidAmount;
    private Long paidAt;
}
