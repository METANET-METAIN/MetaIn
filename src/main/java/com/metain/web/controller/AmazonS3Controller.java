package com.metain.web.controller;

import com.metain.web.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/include")
public class AmazonS3Controller {

    private final AwsS3Service awsS3Service;
//
//    /**
//     * 파일 업로드 페이지로 이동
//     */
//    @GetMapping("/upload")
//    public String uploadFilePage() {
//        return "upload-file";
//    }
//
//    // 다른 메소드들은 동일하게 유지
//
//    // 파일 삭제 페이지로 이동
//    @GetMapping("/delete")
//    public String deleteFilePage() {
//        return "delete-file";
//    }
//
//    // 파일 업데이트 페이지로 이동
//    @GetMapping("/update")
//    public String updateFilePage() {
//        return "update-file";
//    }

    /**
     * Amazon S3에 파일 업로드
     *
     * @param file 업로드할 파일
     * @return 성공 시 200 Success와 함께 업로드된 파일의 URL 반환
     */


    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String uploadedUrl = awsS3Service.uploadS3File(file, file.getName());
        return ResponseEntity.ok(uploadedUrl);
    }
}


//
//    /**
//     * Amazon S3에 업로드된 파일 삭제
//     * @param urlToDelete 삭제할 파일의 URL
//     * @return 성공 시 200 Success
//     */
//    @DeleteMapping("/delete")
//    public ResponseEntity<Void> deleteFile(@RequestParam("url") String urlToDelete) {
//        awsS3Service.deleteFile(urlToDelete);
//        return ResponseEntity.ok().build();
//    }
//
//    /**
//     * Amazon S3에 업로드된 파일 업데이트
//     * @param oldUrlToUpdate 업데이트할 파일의 기존 URL
//     * @param file 업데이트할 파일
//     * @return 성공 시 200 Success와 함께 업데이트된 파일의 URL 반환
//     */
//    @PutMapping("/update")
//    public ResponseEntity<String> updateFile(@RequestParam("oldUrl") String oldUrlToUpdate,
//                                             @RequestParam("file") MultipartFile file) throws IOException {
//        String updatedUrl = awsS3Service.updateS3File(oldUrlToUpdate, file);
//        return ResponseEntity.ok(updatedUrl);
//    }
//}
