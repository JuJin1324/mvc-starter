package practice.mvcstarter.domain.users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.boards.dto.BoardPostReadDto;
import practice.mvcstarter.domain.boards.dto.BoardReadDto;
import practice.mvcstarter.domain.boards.dto.PostCommentReadDto;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/14
 */
public interface UserBoardService {
    /**
     * 관심 게시판 조회
     */
    Page<BoardReadDto> getLikedBoardPage(Long memberId, Pageable pageable);

    /**
     * 관심 게시글 조회
     */
    Page<BoardPostReadDto> getLikedPostPage(Long memberId, Pageable pageable);

    /**
     * 내가 쓴 게시글 조회
     */
    Page<BoardPostReadDto> getWrittenByMePostPage(Long memberId, Pageable pageable);

    /**
     * 관심 게시글 체크 저장
     */
    void saveLikedPost(Long memberId, Long postId, boolean isLiked);

    /**
     * 내가 쓴 게시글의 댓글 조회
     */
    Page<PostCommentReadDto> getWrittenByMeCommentPage(Long memberId, Pageable pageable);
}
