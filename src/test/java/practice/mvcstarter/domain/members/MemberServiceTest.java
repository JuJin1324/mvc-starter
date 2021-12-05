package practice.mvcstarter.domain.members;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.mvcstarter.domain.teams.TeamDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private static final String MEMBER_NAME = "회원 이름";
    private static final String MEMBER_NICK_NAME = "회원 닉네임";
    private static final int MEMBER_AGE          = 20;

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("[회원 생성] 1.매개변수가 null 인 경우")
    void createMember_whenParamIsNull_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> memberService.createMember(null));
    }

    @Test
    @DisplayName("[회원 생성] 2.dto validation 미통과")
    void createMember_whenInvalidParam_thenThrowException() {
        /* 이름 blank */
        assertThrows(IllegalArgumentException.class, () ->
                memberService.createMember(MemberDto.toCreate(null, null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                memberService.createMember(MemberDto.toCreate("", null, null)));

        /* Name 이 blank */
        assertThrows(IllegalArgumentException.class, () ->
                memberService.createMember(MemberDto.toCreate(null, null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                memberService.createMember(MemberDto.toCreate("", null, null)));

        /* NickName 이 blank */
        assertThrows(IllegalArgumentException.class, () ->
                memberService.createMember(MemberDto.toCreate(MEMBER_NAME, null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                memberService.createMember(MemberDto.toCreate(MEMBER_NAME, "", null)));

        /* age 가 null */
        assertThrows(IllegalArgumentException.class, () ->
                memberService.createMember(MemberDto.toCreate(MEMBER_NAME, MEMBER_NICK_NAME, null)));
    }

    @Test
    @DisplayName("[회원 생성] 3.dto validation 통과")
    void createMember_whenValidParam_thenReturnTeamId() {
        /* given */
        MemberDto givenDto = MemberDto.toCreate(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);

        /* when */
        memberService.createMember(givenDto);

        /* then */
        verify(memberRepository, times(1)).save(any());
    }
}
