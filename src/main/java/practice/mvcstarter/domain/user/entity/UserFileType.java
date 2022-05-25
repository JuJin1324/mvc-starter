package practice.mvcstarter.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@AllArgsConstructor
@Getter
public enum UserFileType {
    PROFILE("P"),
    ;

    private final String value;
}
