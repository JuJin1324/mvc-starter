package practice.mvcstarter.domain.members;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.boards.BoardDto;

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
    Long createMember(MemberDto dto);

    /**
     * 회원 조회 - 단건
     */
    MemberDto getSingleMember(Long memberId);

    /**
     * 회원 갱신
     */
    void updateMember(Long memberId, MemberDto dto);

    /**
     * 회원 갱신 - 프로필
     */
    void updateMemberProfile(Long memberId, MemberDto dto);

    /**
     * 회원 삭제
     */
    void deleteMember(Long memberId);
}
