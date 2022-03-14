package practice.mvcstarter.domain.files.repository;

import practice.mvcstarter.domain.files.entity.FileStore;
import practice.mvcstarter.global.repository.CommonRepository;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
public interface FileRepository extends CommonRepository<FileStore, Long> {

    Optional<FileStore> findById(Long fileId);

    <S extends FileStore> S save(S entity);

    void delete(FileStore entity);
}
