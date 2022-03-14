package practice.mvcstarter.domain.members.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import practice.mvcstarter.domain.boards.dto.BoardPostReadDto;
import practice.mvcstarter.domain.boards.dto.BoardReadDto;
import practice.mvcstarter.domain.boards.dto.PostCommentReadDto;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/14
 */
public interface MemberBoardService {
    /**
     * 관심 게시판 조회
     */
    Page<BoardReadDto> getLikedBoardList(Long memberId, Pageable pageable);

    /**
     * 관심 게시글 조회
     */
    Page<BoardPostReadDto> getLikedPostList(Long memberId, Pageable pageable);

    /**
     * 내가 쓴 게시글 조회
     */
    Page<BoardPostReadDto> getWrittenByMePostList(Long memberId, Pageable pageable);

    /**
     * 관심 게시글 체크 저장
     */
    void saveLikedPost(Long memberId, Long postId, boolean isLiked);

    /**
     * 내가 쓴 게시글의 댓글 조회
     */
    Page<PostCommentReadDto> getWrittenByMeCommentList(Long memberId, Pageable pageable);
}
