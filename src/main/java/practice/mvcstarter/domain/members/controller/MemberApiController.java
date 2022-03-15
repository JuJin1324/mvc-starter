package practice.mvcstarter.domain.members.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mvcstarter.domain.boards.dto.BoardPostReadDto;
import practice.mvcstarter.domain.boards.dto.BoardReadDto;
import practice.mvcstarter.domain.boards.dto.PostCommentReadDto;
import practice.mvcstarter.domain.members.dto.*;
import practice.mvcstarter.domain.members.service.MemberBoardService;
import practice.mvcstarter.domain.members.service.MemberService;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@RestController
@RequestMapping("/api/v1.0/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService      memberService;
    private final MemberBoardService memberBoardService;

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
     * 관심 게시판 조회 - 페이지
     */
    @GetMapping("/{memberId}/boards/like")
    public Page<BoardReadDto> getLikedBoardList(@PathVariable("memberId") Long memberId,
                                                @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberBoardService.getLikedBoardPage(memberId, pageable);
    }

    /**
     * 관심 게시글 조회 - 페이지
     */
    @GetMapping("/{memberId}/boards/posts/like")
    public Page<BoardPostReadDto> getLikedPostList(@PathVariable("memberId") Long memberId,
                                                   @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberBoardService.getLikedPostPage(memberId, pageable);
    }

    /**
     * 내가 쓴 게시글 조회 - 페이지
     */
    @GetMapping("/{memberId}/boards/posts/mine")
    public Page<BoardPostReadDto> getWrittenByMePostList(@PathVariable("memberId") Long memberId,
                                                         @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberBoardService.getWrittenByMePostPage(memberId, pageable);
    }

    /**
     * 관심 게시글 체크 저장
     */
    @PostMapping("/{memberId}/boards/posts/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveLikedPost(@PathVariable("memberId") Long memberId,
                              @PathVariable("postId") Long postId,
                              @Validated @RequestBody LikedPostSaveDto reqBody) {
        memberBoardService.saveLikedPost(memberId, postId, reqBody.getIsLiked());
    }

    /**
     * 내가 쓴 게시글의 댓글 조회
     */
    @GetMapping("/{memberId}/boards/posts/comments/mine")
    public Page<PostCommentReadDto> getWrittenByMeCommentList(@PathVariable("memberId") Long memberId,
                                                              @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return memberBoardService.getWrittenByMeCommentPage(memberId, pageable);
    }
}
