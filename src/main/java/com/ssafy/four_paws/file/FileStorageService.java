package com.ssafy.four_paws.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String saveFile(MultipartFile file) throws IOException {
        // 파일 이름 정리
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());

        // 파일 이름에 UUID를 추가하여 고유하게 만듦
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // 파일 유형 검사 (예: jpg, jpeg, png, gif)
        String fileExtension = StringUtils.getFilenameExtension(fileName).toLowerCase();
        if (!fileExtension.equals("jpg") && !fileExtension.equals("jpeg") &&
                !fileExtension.equals("png") && !fileExtension.equals("gif")) {
            throw new IOException("지원하지 않는 파일 형식입니다.");
        }

        // 파일 크기 제한 (예: 10MB)
        if (file.getSize() > 10 * 1024 * 1024) { // 10MB
            throw new IOException("파일 크기가 너무 큽니다. 최대 10MB까지 허용됩니다.");
        }

        // 업로드 디렉토리 경로 설정
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        // 디렉토리가 존재하지 않으면 생성
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 파일을 디렉토리에 저장
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 파일의 접근 가능한 URL 생성 (예: http://localhost:8080/uploads/{fileName})
        String fileDownloadUri = "/uploads/" + fileName;

        return fileDownloadUri;
    }
}
