package practice.mvcstarter.domain.members.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/14
 */

@ExtendWith(MockitoExtension.class)
class MemberBoardServiceTest {
    private MemberBoardService memberBoardService;
    
    @BeforeEach
    void setUp() {
        // TODO
    }

    @Test
    @DisplayName("[관심 게시판 조회] 1.유효하지 않은 매개변수")
    void getLikedBoardList_whenInvalidParam_thenThrowException() {
        // TODO
    }

    @Test
    @DisplayName("[관심 게시글 조회] 1.유효하지 않은 매개변수")
    void getLikedPostList_whenInvalidParam_thenThrowException() {
        // TODO
    }

    @Test
    @DisplayName("[내가 쓴 게시글 조회] 1.유효하지 않은 매개변수")
    void getWrittenByMePostList_whenInvalidParam_thenThrowException() {
        // TODO
    }

    @Test
    @DisplayName("[관심 게시글 체크 저장] 1.유효하지 않은 매개변수")
    void saveLikedPost_whenInvalidParam_thenThrowException() {
        // TODO
    }

    @Test
    @DisplayName("[내가 쓴 게시글의 댓글 조회] 1.유효하지 않은 매개변수")
    void getWrittenByMeCommentList_whenInvalidParam_thenThrowException() {
        // TODO
    }
}
