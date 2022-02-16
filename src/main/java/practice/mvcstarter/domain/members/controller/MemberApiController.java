package practice.mvcstarter.domain.members.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mvcstarter.domain.boards.dto.BoardReadDto;
import practice.mvcstarter.domain.members.service.MemberService;
import practice.mvcstarter.domain.members.dto.MemberCreateDto;
import practice.mvcstarter.domain.members.dto.MemberReadDto;
import practice.mvcstarter.domain.members.dto.MemberUpdateDto;
import practice.mvcstarter.domain.members.dto.MemberUpdateProfileDto;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@RestController
@RequestMapping("/api/v1.0/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원 생성
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createMember(@Validated @RequestBody MemberCreateDto dto) {
        return memberService.createMember(dto);
    }

    /**
     * 회원 조회 - 단건
     */
    @GetMapping("/{memberId}")
    public MemberReadDto getSingleMember(@PathVariable("memberId") Long memberId) {
        return memberService.getSingleMember(memberId);
    }

    /**
     * 회원 갱신
     */
    @PutMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMember(@PathVariable("memberId") Long memberId,
                             @Validated @RequestBody MemberUpdateDto dto) {
        memberService.updateMember(memberId, dto);
    }

    /**
     * 회원 갱신 - 프로필
     */
    @PutMapping("/{memberId}/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMemberProfile(@PathVariable("memberId") Long memberId,
                                    @Validated @RequestBody MemberUpdateProfileDto dto) {
        memberService.updateMemberProfile(memberId, dto);
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
    }


    /**
     * 관심 게시글 조회 - 페이지
     */
    @GetMapping("/{memberId}/boards/attractive")
    public Page<BoardReadDto> getAttractiveBoards(@PathVariable("memberId") Long memberId) {
        return null;
    }

    /**
     * 내가 쓴 게시글 조회 - 페이지
     */
    @GetMapping("/{memberId}/boards/mine")
    public Page<BoardReadDto> getMineBoards(@PathVariable("memberId") Long memberId) {
        return null;
    }

//    /**
//     * 회원 조회 - 페이지
//     */
//    @GetMapping("")
//    public Page<GetSingleMemberResBody> getMemberPage(@PageableDefault(size = 20) Pageable pageable) {
//        return memberService.getMemberPage(pageable)
//                .map(GetSingleMemberResBody::createReqBody);
//    }
}
