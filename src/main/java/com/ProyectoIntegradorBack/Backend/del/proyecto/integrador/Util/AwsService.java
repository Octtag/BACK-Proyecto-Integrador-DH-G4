package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Util;

import com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Jwt.JwtUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class AwsService {

    @Autowired
    SaverToS3 saverToS3;
    @Autowired
    JwtUtil jwtUtil;

    public String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            sb.append(randomChar);
        }

        return sb.toString();
    }

    public String uploadEventImage(MultipartFile imageFile) throws IOException {
        String s3Folder = "img/destinos/";

        // Obteniendo la extensión del archivo
        String fileExtension = FilenameUtils.getExtension(imageFile.getOriginalFilename());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS");
        Date now = new Date();
        String formattedDate = dateFormat.format(now);
        String randomDigits = generateRandomDigits(8);

        String objectKey = s3Folder + "eventImage_" + formattedDate + "_" + randomDigits + "." + fileExtension;

        byte[] imageData = imageFile.getBytes();

        saverToS3.saveToS3(imageData, "simplepassbucket", objectKey);

        String url = "https://simplepassbucket.s3.amazonaws.com/" + objectKey;
        return url;
    }
}


