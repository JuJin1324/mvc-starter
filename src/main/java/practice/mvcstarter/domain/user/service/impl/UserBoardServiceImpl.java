package practice.mvcstarter.domain.user.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.board.dto.BoardPostReadDto;
import practice.mvcstarter.domain.board.dto.BoardReadDto;
import practice.mvcstarter.domain.board.dto.PostCommentReadDto;
import practice.mvcstarter.domain.user.service.UserBoardService;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/14
 */

@Service
@Transactional
@RequiredArgsConstructor
public class UserBoardServiceImpl implements UserBoardService {
    @Override
    public Page<BoardReadDto> getLikedBoardPage(Long memberId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<BoardPostReadDto> getLikedPostPage(Long memberId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<BoardPostReadDto> getWrittenByMePostPage(Long memberId, Pageable pageable) {
        return null;
    }

    @Override
    public void saveLikedPost(Long memberId, Long postId, boolean isLiked) {

    }

    @Override
    public Page<PostCommentReadDto> getWrittenByMeCommentPage(Long memberId, Pageable pageable) {
        return null;
    }
}
