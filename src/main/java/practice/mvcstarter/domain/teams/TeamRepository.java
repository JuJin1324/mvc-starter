package practice.mvcstarter.domain.teams;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.CommonRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/15
 */
public interface TeamRepository extends CommonRepository<Team, Long> {
    Page<Team> findAll(Pageable pageable);

    Optional<Team> findById(Long aLong);

    <S extends Team> S save(S entity);

    void delete(Team entity);

    List<Team> findAllByNameLike(String nameLike);

    void deleteAll(Iterable<? extends Team> entities);
}
