package com.ProyectoIntegradorBack.Backend.del.proyecto.integrador.Util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Component
public class SaverToS3 {

    @Autowired
    AwsProperties awsProperties;
    private S3Client s3Client;

    @Autowired
    public SaverToS3(AwsProperties awsProperties) {
        this.awsProperties = awsProperties;
    }

    @PostConstruct
    private void init() {
        AwsBasicCredentials credentials = AwsBasicCredentials.create(
                awsProperties.getAccessKey(),
                awsProperties.getSecretKey()
        );

        s3Client = S3Client.builder()
                .region(Region.SA_EAST_1) // Cambia según tu región
                .credentialsProvider(() -> credentials)
                .build();
    }

    public void saveToS3(byte[] pngData, String bucketName, String objectKey) {
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(objectKey)
                        .build(),
                RequestBody.fromByteBuffer(ByteBuffer.wrap(pngData)));
    }

    public byte[] getQRImage(String bucketName, String objectKey) throws IOException {
        // Inicia una solicitud de obtención de objeto
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        // Llama al método getObject, que devuelve una respuesta con los datos del objeto
        ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);

        // Devuelve los datos como un array de bytes
        return objectBytes.asByteArray();
    }

    public String saveImageToS3(MultipartFile imageFile, String folderName) throws IOException {
        String s3Folder = "img/destino/" + folderName + "/";

        // Obteniendo la extensión del archivo
        String fileExtension = FilenameUtils.getExtension(imageFile.getOriginalFilename());

        // Generar nombre único para el archivo
        String formattedDate = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String randomDigits = generateRandomDigits(8);
        String objectKey = s3Folder + "productImage_" + formattedDate + "_" + randomDigits + "." + fileExtension;

        byte[] imageData = imageFile.getBytes();
        s3Client.putObject(PutObjectRequest.builder()
                        .bucket("simplepassbucket")
                        .key(objectKey)
                        .build(),
                RequestBody.fromByteBuffer(ByteBuffer.wrap(imageData)));

        return "https://simplepassbucket.s3.amazonaws.com/" + objectKey;
    }
    private String generateRandomDigits(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

}
