package practice.mvcstarter.domain.users.controller;

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
import practice.mvcstarter.domain.users.dto.*;
import practice.mvcstarter.domain.users.service.UserBoardService;
import practice.mvcstarter.domain.users.service.UserService;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@RestController
@RequestMapping("/api/v1.0/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService      userService;
    private final UserBoardService userBoardService;

    /**
     * 회원 생성
     */
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@Validated @RequestBody UserCreateDto dto) {
        return userService.createMember(dto);
    }

    /**
     * 회원 조회 - 단건
     */
    @GetMapping("/{userId}")
    public UserReadDto getSingleUser(@PathVariable("userId") Long userId) {
        return userService.getSingleUser(userId);
    }

    /**
     * 회원 갱신
     */
    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("userId") Long userId,
                           @Validated @RequestBody UserUpdateDto dto) {
        userService.updateUser(userId, dto);
    }

    /**
     * 회원 갱신 - 프로필
     */
    @PutMapping("/{userId}/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProfile(@PathVariable("userId") Long userId,
                              @Validated @RequestBody UserUpdateProfileDto dto) {
        userService.updateProfile(userId, dto);
    }

    /**
     * 회원 삭제
     */
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(@PathVariable("userId") Long userId) {
        userService.deleteUser(userId);
    }

    /**
     * 관심 게시판 조회 - 페이지
     */
    @GetMapping("/{userId}/boards/like")
    public Page<BoardReadDto> getLikedBoardList(@PathVariable("userId") Long userId,
                                                @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return userBoardService.getLikedBoardPage(userId, pageable);
    }

    /**
     * 관심 게시글 조회 - 페이지
     */
    @GetMapping("/{userId}/boards/posts/like")
    public Page<BoardPostReadDto> getLikedPostList(@PathVariable("userId") Long userId,
                                                   @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return userBoardService.getLikedPostPage(userId, pageable);
    }

    /**
     * 내가 쓴 게시글 조회 - 페이지
     */
    @GetMapping("/{userId}/boards/posts/mine")
    public Page<BoardPostReadDto> getWrittenByMePostList(@PathVariable("userId") Long userId,
                                                         @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return userBoardService.getWrittenByMePostPage(userId, pageable);
    }

    /**
     * 관심 게시글 체크 저장
     */
    @PostMapping("/{userId}/boards/posts/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveLikedPost(@PathVariable("userId") Long userId,
                              @PathVariable("postId") Long postId,
                              @Validated @RequestBody LikedPostSaveDto reqBody) {
        userBoardService.saveLikedPost(userId, postId, reqBody.getIsLiked());
    }

    /**
     * 내가 쓴 게시글의 댓글 조회
     */
    @GetMapping("/{userId}/boards/posts/comments/mine")
    public Page<PostCommentReadDto> getWrittenByMeCommentList(@PathVariable("userId") Long userId,
                                                              @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return userBoardService.getWrittenByMeCommentPage(userId, pageable);
    }
}
