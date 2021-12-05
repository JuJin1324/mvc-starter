package practice.mvcstarter.domain.members;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.mvcstarter.domain.teams.Team;
import practice.mvcstarter.domain.teams.TeamDto;
import practice.mvcstarter.exceptions.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private static final Long   MEMBER_ID           = 777L;
    private static final Long   MEMBER_ID_NOT_EXIST = 999999999L;
    private static final String MEMBER_NAME         = "회원 이름";
    private static final String MEMBER_NICK_NAME  = "회원 닉네임";
    private static final int    MEMBER_AGE        = 20;

    private static final String MEMBER_NAME_NEW = "새로운 회원 이름";
    private static final int    MEMBER_AGE_NEW  = 30;

    private static final String TEAM_NAME = "팀 이름";

    @Mock
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
    }

    @Test
    @DisplayName("[회원 생성] 1.유효하지 않은 매개변수")
    void createMember_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> memberService.createMember(null));

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
    @DisplayName("[회원 생성] 2.정상 생성")
    void createMember_whenNormal_thenReturnMemberId() {
        /* given */
        MemberDto givenDto = MemberDto.toCreate(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);

        /* when */
        memberService.createMember(givenDto);

        /* then */
        verify(memberRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("[회원 조회 - 단건] 1.유효하지 않은 매개변수")
    void getSingleMember_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> memberService.getSingleMember(null));
    }

    @Test
    @DisplayName("[회원 조회 - 단건] 2.존재하지 않는 memberId")
    void getSingleMember_whenNotExistMemberId_thenThrowException() {
        /* given */
        when(memberRepository.findById(MEMBER_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(ResourceNotFoundException.class, () ->
                memberService.getSingleMember(MEMBER_ID_NOT_EXIST));
    }

    @Test
    @DisplayName("[회원 조회 - 단건] 3.팀이 없는 회원 조회")
    void getSingleMember_whenReadMemberHasNoTeam_thenReturnMemberDto() {
        /* given */
        Member givenMember = Member.createMember(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);
        when(memberRepository.findById(MEMBER_ID))
                .thenReturn(Optional.of(givenMember));

        /* when */
        MemberDto dto = memberService.getSingleMember(MEMBER_ID);

        /* then */
        assertThat(dto.getName()).isEqualTo(givenMember.getName());
        assertThat(dto.getNickName()).isEqualTo(givenMember.getNickName());
        assertThat(dto.getAge()).isEqualTo(givenMember.getAge());
    }

    @Test
    @DisplayName("[회원 조회 - 단건] 3.팀이 있는 회원 조회")
    void getSingleMember_whenReadMemberHasTeam_thenReturnMemberDto() {
        /* given */
        Member givenMember = Member.createMember(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);
        Team givenTeam = Team.createTeam(TEAM_NAME);
        givenMember.updateTeam(givenTeam);
        when(memberRepository.findById(MEMBER_ID))
                .thenReturn(Optional.of(givenMember));

        /* when */
        MemberDto dto = memberService.getSingleMember(MEMBER_ID);

        /* then */
        assertThat(dto.getName()).isEqualTo(givenMember.getName());
        assertThat(dto.getNickName()).isEqualTo(givenMember.getNickName());
        assertThat(dto.getAge()).isEqualTo(givenMember.getAge());

        assertThat(dto.getTeam().getTeamName()).isEqualTo(givenTeam.getName());
    }

    @Test
    @DisplayName("[회원 갱신] 1.유효하지 않은 매개변수")
    void updateTeam_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                memberService.updateMember(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                memberService.updateMember(MEMBER_ID, null));

        assertThrows(IllegalArgumentException.class, () ->
                memberService.updateMember(MEMBER_ID, MemberDto.toUpdate(null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                memberService.updateMember(MEMBER_ID, MemberDto.toUpdate("", null)));
        assertThrows(IllegalArgumentException.class, () ->
                memberService.updateMember(MEMBER_ID, MemberDto.toUpdate(MEMBER_NAME_NEW, null)));
    }

    @Test
    @DisplayName("[회원 갱신] 2.존재하지 않는 memberId")
    void updateMember_whenNotExistMemberId_thenThrowException() {
        /* given */
        MemberDto givenDto = MemberDto.toUpdate(MEMBER_NAME_NEW, MEMBER_AGE_NEW);
        when(memberRepository.findById(MEMBER_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(ResourceNotFoundException.class, () ->
                memberService.updateMember(MEMBER_ID_NOT_EXIST, givenDto));
    }

    @Test
    @DisplayName("[회원 갱신] 3.정상 갱신")
    void updateTeam_whenNormal_thenUpdateTeam() {
        /* given */
        Member givenMember = Member.createMember(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);
        when(memberRepository.findById(MEMBER_ID))
                .thenReturn(Optional.of(givenMember));

        MemberDto givenDto = MemberDto.toUpdate(MEMBER_NAME_NEW, MEMBER_AGE_NEW);

        /* when */
        memberService.updateMember(MEMBER_ID, givenDto);

        /* then */
        assertThat(givenMember.getName()).isEqualTo(MEMBER_NAME_NEW);
        assertThat(givenMember.getAge()).isEqualTo(MEMBER_AGE_NEW);
    }
}
