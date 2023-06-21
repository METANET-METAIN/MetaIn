package com.metain.web.service;

import com.metain.web.dto.FileDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private final S3Client s3Client;

//    @Value("${cloud.aws.s3.bucket}")
//    private String bucket;
    private final String bucket = "metains3";

    /**
     * 파일을 Amazon S3에 업로드
     * @param s3File 업로드할 파일
     * @return 업로드된 파일의 URL
     */
    public String uploadS3File(MultipartFile s3File, String fileName) throws IOException {
        // 파일명 생성
//        String s3FileName = UUID.randomUUID() + "-" + s3File.getOriginalFilename();

        // 파일 업로드
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(fileName)
                .build();
        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(s3File.getBytes()));

        // 업로드된 파일의 URL 반환
        return getS3FileUrl(fileName);
    }
//
//    public FileDTO upload(File multipartFile, String dirName, String fileName) throws IOException {
//
//        return upload(multipartFile, fileName);
//    }
//
//    private FileDTO upload(File file, String fileName) {
//        String key = fileName;
//        String path = putS3(file, key);
//        removeFile(file);
//
//        FileDTO fileDto = new FileDTO();
//        fileDto.setFileName(key);
//
//        return fileDto;
////        return FileDTO
////                .set(fileName)
////                .path(path)
////                .build();
//    }
//
//    private String putS3(File uploadFile, String fileName) {
//        s3Client.putObject(PutObjectRequest.builder()
//                .bucket(bucket)
//                .key(fileName)
//                .acl(ObjectCannedACL.PUBLIC_READ)
//                .build(), RequestBody.fromFile(uploadFile));
//
//        return getS3(bucket, fileName);
//    }

//    private String putS3(File uploadFile, String fileName) {
//        s3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//        return getS3(bucket, fileName);
//    }

//    private String getS3(String bucket, String fileName) {
//        AwsCredentialsProvider credentialsProvider = DefaultCredentialsProvider.create();
//        S3Presigner presigner = S3Presigner.builder()
//                .credentialsProvider(credentialsProvider)
//                .build();
//
//        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
//                .bucket(bucket)
//                .key(fileName)
//                .build();
//
//        String url = presigner.presignGetUrl(getUrlRequest).url().toString();
//        presigner.close();
//        return url;
//    }
//
//    private String getS3(String bucket, String fileName) {
//        return s3Client.utilities().getUrl(GetUrlRequest.builder()
//                        .bucket(bucket)
//                        .key(fileName)
//                        .build())
//                .toExternalForm();
//    }

    private void removeFile(File file) {
        file.delete();
    }
//
//    public String getThumbnailPath(String path) {
//
//        GetUrlRequest getUrlRequest = GetUrlRequest.builder()
//                .bucket(bucket)
//                .key(path)
//                .build();
//
//        URL url = s3Client.utilities().getUrl(getUrlRequest);
//        return url.toString();
//    }

    /**
     * Amazon S3에서 파일을 삭제
     * @param urlToDelete 삭제할 파일의 URL
     */
    public void deleteFile(String urlToDelete) {
        // 파일명 추출
        String s3FileName = extractS3FileName(urlToDelete);

        // 파일 삭제
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(s3FileName)
                .build();
        s3Client.deleteObject(deleteObjectRequest);
    }

//    /**
//     * Amazon S3에 업로드된 파일을 업데이트
//     * @param oldUrlToUpdate 업데이트할 파일의 기존 URL
//     * @param newS3File 업데이트할 파일
//     * @return 업데이트된 파일의 URL
//     */
//    public String updateS3File(String oldUrlToUpdate, MultipartFile newS3File) throws IOException {
//        // 기존 파일 삭제
//        deleteFile(oldUrlToUpdate);
//
//        // 파일 업로드
//        return uploadS3File(newS3File);
//    }

    /**
     * Amazon S3에서 파일의 URL 추출
     * @param s3FileName 파일명
     * @return 파일의 URL
     */
    private String getS3FileUrl(String s3FileName) {
        return s3Client.utilities().getUrl(GetUrlRequest.builder()
                        .bucket(bucket)
                        .key(s3FileName)
                        .build())
                .toExternalForm();
    }

    /**
     * URL에서 파일명을 추출
     * @param url 파일의 URL
     * @return 파일명
     */
    private String extractS3FileName(String url) {
        // URL에서 파일명 추출 로직 구현 (예시)
        // 이 예시에서는 UUID로 파일명이 생성되었으므로 UUID 부분을 추출합니다.
        String[] parts = url.split("-");
        if (parts.length > 0) {
            return parts[0] + "-" + parts[1];
        }
        return null;
    }
}
