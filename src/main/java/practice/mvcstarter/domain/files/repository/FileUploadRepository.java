package practice.mvcstarter.domain.files.repository;

import practice.mvcstarter.domain.files.entity.FileUpload;
import practice.mvcstarter.domain.base.repository.CommonRepository;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
public interface FileUploadRepository extends CommonRepository<FileUpload, Long> {

    Optional<FileUpload> findById(Long fileId);

    <S extends FileUpload> S save(S entity);

    void delete(FileUpload entity);
}
