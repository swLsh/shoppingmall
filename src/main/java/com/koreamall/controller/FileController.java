package com.koreamall.controller;

import com.koreamall.dto.FileDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileController {
//    @GetMapping("/image/{uuid}")
//    public ResponseEntity<byte[]> get_image_file(
//            @PathVariable("UUID") String UUID) {
//
//
//        if (Objects.isNull(fileDTO)) {
//            return ResponseEntity.notFound().build();
//        }
//
//        byte[] data = fileDTO.getData();
//        String originalFileNameEncoded = URLEncoder.encode(fileDTO.getOriginalFileName(), StandardCharsets.UTF_8);
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Type", "application/octet-stream");
//        headers.add("Content-Disposition", "attachment; filename=" + originalFileNameEncoded);
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .body(data);
//    }
}