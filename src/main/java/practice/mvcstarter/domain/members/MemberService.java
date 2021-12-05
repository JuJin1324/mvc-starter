package practice.mvcstarter.domain.members;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.exceptions.ResourceNotFoundException;


/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    public static final String RESOURCE_NAME = "Member";

    private final MemberRepository memberRepository;

    /**
     * 회원 생성
     *
     * @return memberId
     */
    @Transactional
    public Long createMember(MemberDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("toCreate Dto is null.");
        }
        dto.validateToCreate();

        Member member = Member.createMember(dto.getName(), dto.getNickName(), dto.getAge());
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 회원 조회 - 단건
     */
     public MemberDto getSingleMember(Long memberId) {
         if (memberId == null) {
             throw new IllegalArgumentException("memberId is null.");
         }

         return memberRepository.findById(memberId)
                 .map(MemberDto::toRead)
                 .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME));
     }

     /**
      * 회원 조회 - 페이지
      */
     public Page<MemberDto> getMemberPage(Pageable pageable) {
         return null;
     }

     /**
      * 회원 갱신
      */
     public void updateMember(Long memberId, MemberDto dto) {

     }

     /**
      * 회원 삭제
      */
     public void deleteMember(Long memberId) {

     }
}
