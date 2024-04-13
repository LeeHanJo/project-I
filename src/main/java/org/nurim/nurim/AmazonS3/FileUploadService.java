package org.nurim.nurim.AmazonS3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Log4j2
public class FileUploadService {

    private final AmazonS3ResourceStorage amazonS3ResourceStorage;

    private final AmazonS3 amazonS3; // Amazon S3 클라이언트

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileDetail save(MultipartFile multipartFile) {
        FileDetail fileDetail = FileDetail.multipartOf(multipartFile);
        amazonS3ResourceStorage.store(fileDetail.getPath(), multipartFile);
        return fileDetail;
    }
    public boolean deleteFile(String uuid) {
        try {
            // S3에서 파일 삭제
            amazonS3.deleteObject(bucket, uuid);
            return true;
        } catch (AmazonServiceException e) {
            // 삭제 실패 시 에러 로그 출력
            log.error("Failed to delete file from S3: " + e.getMessage());
            return false;
        }
    }
}
