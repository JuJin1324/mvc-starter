package practice.mvcstarter.domain.files;

import practice.mvcstarter.exceptions.ExpiredFileException;
import practice.mvcstarter.exceptions.ReadFileException;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
public interface FileService {
    /**
     * 파일 조회 - 단건
     */
    FileDto getFile(Long fileId) throws ExpiredFileException, ReadFileException;
}
