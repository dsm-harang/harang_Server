package com.javaproject.harang.service.image;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class ImageServiceImpl implements ImageService{

    @Value("${image.file.path}")
    private String imagePath;

    @SneakyThrows
    @Override
    public byte[] getImage(String fileName) {
        File file = new File(imagePath, fileName);

        if (!file.exists())
            throw new RuntimeException();

        InputStream inputStream = new FileInputStream(file);

        return IOUtils.toByteArray(inputStream);
    }

}
