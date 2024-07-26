package com.koreamall.converter;


import com.koreamall.dto.FileDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class ImageFileConverter implements Converter<MultipartFile, FileDTO> {
    @Override
    public FileDTO convert(MultipartFile file) {
        System.out.println("ImageFileConverter 실행...");
        String uuid = UUID.randomUUID().toString();
        String fileName = file.getOriginalFilename();
        if(file.isEmpty()){
            System.out.println("파일이 존재하지 않음..");
            return null;
        }
        byte[] data = null;
        try {
            data = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FileDTO.builder()
                .UUID(uuid)
                .originalFileName(fileName)
                .data(data)
                .build();
    }
}
