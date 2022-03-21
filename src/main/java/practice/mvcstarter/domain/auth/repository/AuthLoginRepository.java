package practice.mvcstarter.domain.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import practice.mvcstarter.domain.auth.entity.AuthLogin;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/21
 */
public interface AuthLoginRepository extends JpaRepository<AuthLogin, Long> {

}
