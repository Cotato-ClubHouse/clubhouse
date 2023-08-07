package clubhouse.clubhouse.global.S3;

import clubhouse.clubhouse.domain.form.exception.ErrorCode;
import clubhouse.clubhouse.domain.form.exception.ImageException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Uploader {

    private final AmazonS3Client amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFiles(MultipartFile multipartFile, String dirName) throws IOException {
        log.info("upload Files {}",multipartFile);

        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new ImageException(ErrorCode.IMAGE_PROCESSING_FAIL));

        return upload(uploadFile, dirName);
    }

    private String upload(File uploadFile, String dirName) {
        String fileName = dirName + "/" + uploadFile.getName();
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);
        log.info(uploadImageUrl);
        return uploadImageUrl;
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws ImageException {
//        File convertFile = new File(file.getOriginalFilename());
        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        log.info("original file name: {}",file.getOriginalFilename());

        try {
            if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
                log.info("try to create File");
                FileOutputStream fos = new FileOutputStream(convertFile); // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
                log.info("success to write");
                fos.close();
                log.info("success to close");
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            throw new ImageException(ErrorCode.IMAGE_PROCESSING_FAIL);
        }
        return Optional.empty();
    }
}
