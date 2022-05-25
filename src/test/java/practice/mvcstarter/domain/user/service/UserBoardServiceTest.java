package practice.mvcstarter.domain.user.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.mvcstarter.domain.board.exception.PostNotFoundException;
import practice.mvcstarter.domain.user.exception.UserNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/14
 */

@ExtendWith(MockitoExtension.class)
class UserBoardServiceTest {
    private static final Long MEMBER_ID_VALID     = 1L;
    private static final Long MEMBER_ID_NOT_EXIST = 999999999L;
    private static final Long POST_ID_VALID     = 1L;
    private static final Long POST_ID_NOT_EXIST = 999999999L;


    private UserBoardService userBoardService;

    @BeforeEach
    void setUp() {
        // TODO: init MemberBoardService
    }

    @Test
    @DisplayName("[관심 게시판 조회] 1.유효하지 않은 매개변수")
    void getLikedBoardList_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.getLikedBoardPage(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.getLikedBoardPage(MEMBER_ID_VALID, null));
    }

    @Test
    @DisplayName("[관심 게시글 조회] 1.유효하지 않은 매개변수")
    void getLikedPostList_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.getLikedPostPage(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.getLikedPostPage(MEMBER_ID_VALID, null));
    }

    @Test
    @DisplayName("[내가 쓴 게시글 조회] 1.유효하지 않은 매개변수")
    void getWrittenByMePostList_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.getWrittenByMePostPage(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.getWrittenByMePostPage(MEMBER_ID_VALID, null));
    }

    @Test
    @DisplayName("[관심 게시글 체크 저장] 1.유효하지 않은 매개변수")
    void saveLikedPost_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.saveLikedPost(null, null, true));
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.saveLikedPost(MEMBER_ID_VALID, null, true));
    }

    @Test
    @DisplayName("[관심 게시글 체크 저장] 2.존재하지 않는 Member ID")
    void saveLikedPost_whenMemberIdIsNotExist_thenThrowException() {
        assertThrows(UserNotFoundException.class, () ->
                userBoardService.saveLikedPost(MEMBER_ID_NOT_EXIST, POST_ID_VALID, true));
    }

    @Test
    @DisplayName("[관심 게시글 체크 저장] 3.존재하지 않는 Post ID")
    void saveLikedPost_whenPostIdIsNotExist_thenThrowException() {
        assertThrows(PostNotFoundException.class, () ->
                userBoardService.saveLikedPost(MEMBER_ID_VALID, POST_ID_NOT_EXIST, true));
    }

    @Test
    @DisplayName("[관심 게시글 체크 저장] 4.게시글 관심 설정")
    void saveLikedPost_whenSetPostLiked_then() {
        // TODO
        userBoardService.saveLikedPost(MEMBER_ID_VALID, POST_ID_VALID, true);
    }

    @Test
    @DisplayName("[관심 게시글 체크 저장] 5.게시글 관심 설정 해제")
    void saveLikedPost_whenSetPostNotLiked_then() {
        // TODO
        userBoardService.saveLikedPost(MEMBER_ID_VALID, POST_ID_VALID, false);
    }

    @Test
    @DisplayName("[내가 쓴 게시글의 댓글 조회] 1.유효하지 않은 매개변수")
    void getWrittenByMeCommentList_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.getWrittenByMeCommentPage(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                userBoardService.getWrittenByMeCommentPage(MEMBER_ID_VALID, null));
    }
}
