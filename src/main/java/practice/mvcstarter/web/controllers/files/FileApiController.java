package practice.mvcstarter.web.controllers.files;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;
import practice.mvcstarter.domain.files.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.files.dto.FileResourceReadDto;
import practice.mvcstarter.domain.files.service.FileService;

import java.nio.charset.StandardCharsets;

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
        FileResourceReadDto fileResource = fileService.getFileResource(fileId);

        /* 한글 깨짐 처리 */
        String encodedFileName = UriUtils.encode(fileResource.getFileName(), StandardCharsets.UTF_8);
        String contentDisposition = String.format("attachment; filename=\"%s\"", encodedFileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(fileResource.getResource());
    }

    /**
     * 파일 조회 - base64 image
     */
    @GetMapping("/{fileId}/base64")
    public FileBase64ReadDto getFileBase64(@PathVariable("fileId") Long fileId) {
        return fileService.getFileBase64(fileId);
    }
}
