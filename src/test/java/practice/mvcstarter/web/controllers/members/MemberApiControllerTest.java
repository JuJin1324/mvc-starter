package practice.mvcstarter.web.controllers.members;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.web.controllers.initdb.InitMember;
import practice.mvcstarter.web.controllers.initdb.InitTeam;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@ActiveProfiles("test")
@Transactional
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MemberApiControllerTest {
    public static final  Long   MEMBER_ID_NOT_EXIST = 999999999L;
    private static final String MEMBER_NAME         = InitMember.MEMBER_NAME;
    private static final String TEAM_NAME_NEW     = "새로운 팀 이름";
    private static final int    TEAM_TOTAL_COUNT  = InitTeam.TEAM_TOTAL_COUNT;
}
