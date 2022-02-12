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
    private static final Long   FILE_ID_VALID   = 1L;
    private static final String FILE_PATH_TEXT  = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files/plainText.txt";
    private static final String FILE_PATH_IMAGE = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files/image.png";
    private static final String FILE_PATH_NOT_EXIST = "/Users/J.Reo/Documents/dev/workspace-git-spring/mvc-starter/src/test/resources/files/notExistFile.txt";


    @Mock
    FileRepository fileRepository;

    FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new LocalFileService(fileRepository);
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
