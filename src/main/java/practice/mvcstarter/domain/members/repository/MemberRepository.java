package practice.mvcstarter.domain.members.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.mvcstarter.global.repository.CommonRepository;
import practice.mvcstarter.domain.members.entity.Member;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */
public interface MemberRepository extends CommonRepository<Member, Long> {

    Optional<Member> findById(Long id);

    @Query("select m from Member m " +
            "left join fetch m.memberFiles mf " +
            "left join fetch mf.file " +
            "where m.id = :id")
    Optional<Member> findWithMemberFilesById(@Param("id") Long id);

    <S extends Member> S save(S entity);

    void delete(Member entity);
}
