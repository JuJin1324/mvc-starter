package practice.mvcstarter.domain.auth.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.mvcstarter.domain.base.entity.TimeBaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/21
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthLogin extends TimeBaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    private String loginID;

    private String password;

    private Long userId;

    private String loginToken;

    private LocalDateTime tokenExpireDateUTC;
}
