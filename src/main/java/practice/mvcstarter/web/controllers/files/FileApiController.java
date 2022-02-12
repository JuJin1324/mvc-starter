package practice.mvcstarter.web.controllers.files;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;
import practice.mvcstarter.domain.files.FileDto;
import practice.mvcstarter.domain.files.FileService;
import practice.mvcstarter.exceptions.FileIsNotBase64Exception;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@RestController
@RequestMapping("/api/v1.0/files")
@RequiredArgsConstructor
public class FileApiController {
    private final FileService fileService;

    /**
     * 파일 조회 - 다운로드
     */
    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> getFileDownload(@PathVariable("fileId") Long fileId) {
        FileDto file = fileService.getFile(fileId);

        /* 한글 깨짐 처리 */
        String encodedFileName = UriUtils.encode(file.getFileName(), StandardCharsets.UTF_8);
        String contentDisposition = String.format("attachment; filename=\"%s\"", encodedFileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(file.getResource());
    }

    /**
     * 파일 조회 - base64 image
     */
    @GetMapping("/{fileId}/base64")
    public GetFileBase64ResBody getFileBase64(@PathVariable("fileId") Long fileId) throws IOException {
        FileDto file = fileService.getFile(fileId);
        if (!file.isImage()) {
            throw new FileIsNotBase64Exception(file.getFileName());
        }
        return GetFileBase64ResBody.createResBody(file);
    }

    @Data
    @AllArgsConstructor
    static class GetFileBase64ResBody {
        private String        fileName;
        private String        contentType;
        private String        base64Image;
        private LocalDateTime expiredTimeKST;

        public static GetFileBase64ResBody createResBody(FileDto fileDto) {
            return new GetFileBase64ResBody(fileDto.getFileName(), fileDto.getContentType().getValue(),
                    fileDto.getBase64Image(), fileDto.getExpiredTimeKST());
        }
    }
}
