package practice.mvcstarter.domain.members.service;

import practice.mvcstarter.domain.members.dto.MemberCreateDto;
import practice.mvcstarter.domain.members.dto.MemberReadDto;
import practice.mvcstarter.domain.members.dto.MemberUpdateDto;
import practice.mvcstarter.domain.members.dto.MemberUpdateProfileDto;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

public interface MemberService {
    /**
     * 회원 생성
     *
     * @return memberId
     */
    Long createMember(MemberCreateDto dto);

    /**
     * 회원 조회 - 단건
     */
    MemberReadDto getSingleMember(Long memberId);

    /**
     * 회원 갱신
     */
    void updateMember(Long memberId, MemberUpdateDto dto);

    /**
     * 회원 갱신 - 프로필
     */
    void updateMemberProfile(Long memberId, MemberUpdateProfileDto dto);

    /**
     * 회원 삭제
     */
    void deleteMember(Long memberId);
}
