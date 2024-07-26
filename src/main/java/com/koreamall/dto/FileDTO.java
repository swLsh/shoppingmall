package com.koreamall.dto;

import lombok.*;

import java.util.Base64;

@Getter
@Setter
@Builder
@ToString(exclude = "data")
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {
    private String UUID;
    private String originalFileName; // 실제 파일명
    private String saveFileName; // 저장되는 파일명
    private byte[] data; //실제 파일 데이터
    private String url; // imageUrl (Base64)

    public void setData(byte[] data){
        this.data = data;
        try {
            this.url = Base64.getEncoder().encodeToString(data);
        }catch (Exception e){

        }
    }
}
