package com.koreamall.converter;


import com.koreamall.dto.FileDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class FilesConverter implements Converter<List<MultipartFile>, List<FileDTO>> {
    @Override
    public List<FileDTO> convert(List<MultipartFile> files) {
        System.out.println("FilesConverter 실행...");
        return files.parallelStream().map(file -> {
            String uuid = UUID.randomUUID().toString();
            String fileName = file.getOriginalFilename();
            String saveFileName = uuid + "_" + fileName;
            byte[] data = null;
            try {
                data = file.getBytes();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return FileDTO.builder()
                    .UUID(uuid)
                    .originalFileName(fileName)
                    .saveFileName(saveFileName)
                    .data(data)
                    .build();
        }).toList();
    }
}
