package practice.mvcstarter.domain.files;

import practice.mvcstarter.domain.CommonRepository;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */
public interface FileRepository extends CommonRepository<File, Long> {

    Optional<File> findById(Long fileId);

    <S extends File> S save(S entity);
}
