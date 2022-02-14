package practice.mvcstarter.domain.members.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/02/12
 */

@AllArgsConstructor
@Getter
public enum MemberFileType {
    PROFILE("P"),
    ;

    private final String value;
}
