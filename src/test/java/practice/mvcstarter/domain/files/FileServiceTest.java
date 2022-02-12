package practice.mvcstarter.domain.files;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.mvcstarter.exceptions.ExpiredFileException;
import practice.mvcstarter.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@ExtendWith(MockitoExtension.class)
class FileServiceTest {
    private static final Long   FILE_ID_VALID       = 1L;
    private static final String FILE_PATH_TEXT      = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files/plainText.txt";
    private static final String FILE_PATH_IMAGE     = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files/image.png";
    private static final String FILE_PATH_NOT_EXIST = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files/notExistFile.txt";

    private static final String      FILE_NAME_VALID  = "파일 이름.png";
    private static final ContentType CONTENT_TYPE_PNG = ContentType.IMAGE_PNG;
    private static final String      BASE64_IMAGE     = "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAKCAYAAACJxx+AAAABYWlDQ1BrQ0dDb2xvclNwYWNlRGlzcGxheVAzAAAokWNgYFJJLCjIYWFgYMjNKykKcndSiIiMUmB/yMAOhLwMYgwKicnFBY4BAT5AJQwwGhV8u8bACKIv64LMOiU1tUm1XsDXYqbw1YuvRJsw1aMArpTU4mQg/QeIU5MLikoYGBhTgGzl8pICELsDyBYpAjoKyJ4DYqdD2BtA7CQI+whYTUiQM5B9A8hWSM5IBJrB+API1klCEk9HYkPtBQFul8zigpzESoUAYwKuJQOUpFaUgGjn/ILKosz0jBIFR2AopSp45iXr6SgYGRiaMzCAwhyi+nMgOCwZxc4gxJrvMzDY7v////9uhJjXfgaGjUCdXDsRYhoWDAyC3AwMJ3YWJBYlgoWYgZgpLY2B4dNyBgbeSAYG4QtAPdHFacZGYHlGHicGBtZ7//9/VmNgYJ/MwPB3wv//vxf9//93MVDzHQaGA3kAFSFl7jXH0fsAAACKZVhJZk1NACoAAAAIAAQBGgAFAAAAAQAAAD4BGwAFAAAAAQAAAEYBKAADAAAAAQACAACHaQAEAAAAAQAAAE4AAAAAAAAAkAAAAAEAAACQAAAAAQADkoYABwAAABIAAAB4oAIABAAAAAEAAAAIoAMABAAAAAEAAAAKAAAAAEFTQ0lJAAAAU2NyZWVuc2hvdPoE6XgAAAAJcEhZcwAAFiUAABYlAUlSJPAAAAHTaVRYdFhNTDpjb20uYWRvYmUueG1wAAAAAAA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJYTVAgQ29yZSA2LjAuMCI+CiAgIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgICAgIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiCiAgICAgICAgICAgIHhtbG5zOmV4aWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vZXhpZi8xLjAvIj4KICAgICAgICAgPGV4aWY6UGl4ZWxZRGltZW5zaW9uPjEwPC9leGlmOlBpeGVsWURpbWVuc2lvbj4KICAgICAgICAgPGV4aWY6UGl4ZWxYRGltZW5zaW9uPjg8L2V4aWY6UGl4ZWxYRGltZW5zaW9uPgogICAgICAgICA8ZXhpZjpVc2VyQ29tbWVudD5TY3JlZW5zaG90PC9leGlmOlVzZXJDb21tZW50PgogICAgICA8L3JkZjpEZXNjcmlwdGlvbj4KICAgPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KbJxrmQAAABxpRE9UAAAAAgAAAAAAAAAFAAAAKAAAAAUAAAAFAAAAStvdOSgAAAAWSURBVCgVYmRgYPgPxDgBI1BmoBUAAAAA//8vs7vuAAAAFElEQVRjZGBg+A/EOAEjUGagFQAA8MwKAQlk/YkAAAAASUVORK5CYII=";

    @Mock
    FileRepository fileRepository;

    FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new LocalFileService(fileRepository);
    }

    @Test
    @DisplayName("[파일 업로드 - base64] 1.유효하지 않은 매개변수")
    void uploadBase64_whenInvalidParams_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                fileService.uploadBase64(null, null, null));
        assertThrows(IllegalArgumentException.class, () ->
                fileService.uploadBase64(FILE_NAME_VALID, null, null));
        assertThrows(IllegalArgumentException.class, () ->
                fileService.uploadBase64(FILE_NAME_VALID, CONTENT_TYPE_PNG, null));
    }

    @Test
    @DisplayName("[파일 업로드 - base64] 2.정상 처리")
    void uploadBase64_whenNormal_thenReturnFile() {
        /* when */
        File file = fileService.uploadBase64(FILE_NAME_VALID, CONTENT_TYPE_PNG, BASE64_IMAGE);

        /* then */
        assertThat(file.getUploadFileName()).isEqualTo(FILE_NAME_VALID);
        assertThat(file.getContentType()).isEqualTo(CONTENT_TYPE_PNG);
        assertThat(file.getFileSize()).isEqualTo(BASE64_IMAGE.length());
        assertThat(file.getExpiredTimeUTC()).isNotNull();
    }

    @Test
    @DisplayName("[파일 조회 - 단건] 1.유효하지 않은 매개변수")
    void getFile_whenInvalidParams_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> fileService.getFile(null));
    }

    @Test
    @DisplayName("[파일 조회 - 단건] 2.파일이 DB에 없는 경우")
    void getFile_whenFileIsNotExistInDB_thenThrowException() throws Exception {
        /* given */
        when(fileRepository.findById(FILE_ID_VALID))
                .thenReturn(Optional.empty());

        /* when / then */
        assertThrows(ResourceNotFoundException.class, () -> fileService.getFile(FILE_ID_VALID));
    }

    @Test
    @DisplayName("[파일 조회 - 단건] 3.파일이 디렉터리에 없는 경우")
    void getFile_whenFileIsNotExistInDirectory_thenThrowException() throws Exception {
        /* given */
        File notExistFile = givenFile(ContentType.PLAIN_TEXT, FILE_PATH_NOT_EXIST,
                "plain.txt", 25L,
                LocalDateTime.now(ZoneId.of("UTC")).plusWeeks(2));
        when(fileRepository.findById(FILE_ID_VALID))
                .thenReturn(Optional.of(notExistFile));

        /* when / then */
        assertThrows(ResourceNotFoundException.class, () -> fileService.getFile(FILE_ID_VALID));
    }

    @Test
    @DisplayName("[파일 조회 - 단건] 4.파일의 유효기간이 지난 경우")
    void getFile_whenFileIsExpired_thenThrowException() throws Exception {
        /* given */
        File expiredFile = givenFile(ContentType.PLAIN_TEXT, FILE_PATH_TEXT,
                "plain.txt", 25L,
                LocalDateTime.now(ZoneId.of("UTC")).minusSeconds(10));
        when(fileRepository.findById(FILE_ID_VALID))
                .thenReturn(Optional.of(expiredFile));

        /* when / then */
        assertThrows(ExpiredFileException.class, () -> fileService.getFile(FILE_ID_VALID));
    }

    @Test
    @DisplayName("[파일 조회 - 단건] 5.텍스트 파일인 경우")
    void getFile_whenFileIsText_then() throws Exception {
        /* given */
        File textFile = givenFile(ContentType.PLAIN_TEXT, FILE_PATH_TEXT,
                "plain.txt", 25L,
                LocalDateTime.now(ZoneId.of("UTC")).plusWeeks(2));
        when(fileRepository.findById(FILE_ID_VALID))
                .thenReturn(Optional.of(textFile));

        /* when */
        FileDto file = fileService.getFile(FILE_ID_VALID);

        /* then */
        assertThat(file.isImage()).isFalse();
        assertThat(file.getBase64Image()).isNull();
    }

    @Test
    @DisplayName("[파일 조회 - 단건] 6.이미지 파일인 경우")
    void getFile_whenFileIsImage_then() throws Exception {
        File imageFile = givenFile(ContentType.IMAGE_PNG, FILE_PATH_IMAGE,
                "image.png", 25L,
                LocalDateTime.now(ZoneId.of("UTC")).plusWeeks(2));
        when(fileRepository.findById(FILE_ID_VALID))
                .thenReturn(Optional.of(imageFile));

        /* when */
        FileDto file = fileService.getFile(FILE_ID_VALID);

        /* then */
        assertThat(file.isImage()).isTrue();
        assertThat(file.getBase64Image()).isNotNull();
    }

    private File givenFile(ContentType contentType, String storeFilePath, String uploadFileName, Long fileSize, LocalDateTime expiredTimeUTC) {
        return File.builder()
                .id(FILE_ID_VALID)
                .contentType(contentType)
                .storeFilePath(storeFilePath)
                .uploadFileName(uploadFileName)
                .fileSize(fileSize)
                .expiredTimeUTC(expiredTimeUTC)
                .build();
    }
}
