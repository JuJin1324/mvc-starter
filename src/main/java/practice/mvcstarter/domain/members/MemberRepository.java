package practice.mvcstarter.domain.members;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.CommonRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */
public interface MemberRepository extends CommonRepository<Member, Long> {
    Page<Member> findAll(Pageable pageable);

    Optional<Member> findById(Long id);

    <S extends Member> S save(S entity);

    void delete(Member entity);
}
