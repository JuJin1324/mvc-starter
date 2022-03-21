package practice.mvcstarter.domain.users.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.mvcstarter.domain.base.repository.CommonRepository;
import practice.mvcstarter.domain.users.entity.User;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */
public interface UserRepository extends CommonRepository<User, Long> {

    Optional<User> findById(Long id);

    @Query("select m from User m " +
            "left join fetch m.memberFiles mf " +
            "left join fetch mf.file " +
            "where m.id = :id")
    Optional<User> findWithMemberFilesById(@Param("id") Long id);

    <S extends User> S save(S entity);

    void delete(User entity);
}
