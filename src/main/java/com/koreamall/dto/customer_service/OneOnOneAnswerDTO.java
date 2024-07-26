package com.koreamall.dto.customer_service;


import com.koreamall.dto.FileDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OneOnOneAnswerDTO {
    private Integer no; // PK. 번호
    private String userId; // 답변자
    private Integer oneOnOneNo; // 게시물번호
    private String answer; // 답변내용
    private LocalDateTime writeDatetime;// 답변날짜
    private List<FileDTO> files;
}
