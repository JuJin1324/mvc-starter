package practice.mvcstarter.domain.boards.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mvcstarter.domain.boards.dto.*;
import practice.mvcstarter.domain.boards.service.BoardPostService;
import practice.mvcstarter.domain.boards.service.BoardService;
import practice.mvcstarter.domain.boards.service.PostCommentService;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2022/03/14
 */

@RestController
@RequestMapping("/api/v1.0/boards")
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService       boardService;
    private final BoardPostService   boardPostService;
    private final PostCommentService postCommentService;

    /**
     * 게시판 조회 - 페이지
     */
    @GetMapping("")
    public Page<BoardReadDto> getBoardPage(@PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardService.getBoardPage(pageable);
    }

    /**
     * 게시글 신규
     */
    @PostMapping("/{boardId}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPost(@PathVariable("boardId") Long boardId,
                           @Validated @RequestBody BoardPostCreateDto dto) {
        // TODO: Member ID
        boardPostService.createPost(1L, boardId, dto);
    }

    /**
     * 게시글 조회 - 페이지
     */
    @GetMapping("/{boardId}/posts")
    public Page<BoardPostReadDto> getPostPage(@PathVariable("boardId") Long boardId,
                                              @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return boardPostService.getPostPage(boardId, pageable);
    }

    /**
     * 게시글 조회 - 단건
     */
    @GetMapping("/{boardId}/posts/{postId}")
    public BoardPostReadDto getPost(@PathVariable("boardId") Long boardId,
                                    @PathVariable("postId") Long postId) {
        return boardPostService.getPost(boardId, postId);
    }

    /**
     * 게시글 갱신
     */
    @PutMapping("/{boardId}/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePost(@PathVariable("boardId") String boardId,
                           @PathVariable("postId") Long postId,
                           @Validated @RequestBody BoardPostUpdateDto dto) {
        // TODO: Member ID
        boardPostService.updatePost(1L, postId, dto);
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{boardId}/posts/{postId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePost(@PathVariable("boardId") String boardId,
                           @PathVariable("postId") Long postId) {
        // TODO: Member ID
        boardPostService.deletePost(1L, postId);
    }

    /**
     * 게시글 댓글 신규
     */
    @PostMapping("/{boardId}/posts/{postId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(@PathVariable("boardId") Long boardId,
                              @PathVariable("postId") Long postId,
                              @Validated @RequestBody PostCommentCreateDto dto) {
        // TODO: Member ID
        postCommentService.createComment(1L, postId, dto);
    }

    /**
     * 게시글 대댓글 신규
     */
    @PostMapping("/{boardId}/posts/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createChildComment(@PathVariable("boardId") Long boardId,
                                   @PathVariable("postId") Long postId,
                                   @PathVariable("commentId") Long commentId,
                                   @Validated @RequestBody PostCommentCreateDto dto) {
        // TODO: Member ID
        postCommentService.createChildComment(1L, postId, commentId, dto);
    }

    /**
     * 게시글 댓글 및 대댓글 조회
     */
    @GetMapping("/{boardId}/posts/{postId}/comments")
    public Page<PostCommentReadDto> getCommentPage(@PathVariable("boardId") Long boardId,
                                                   @PathVariable("postId") Long postId,
                                                   @PageableDefault(size = 30, sort = "lastModifiedDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return postCommentService.getCommentPage(postId, pageable);
    }

    /**
     * 게시글 댓글 수정
     */
    @PutMapping("/{boardId}/posts/{postId}/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateComment(@PathVariable("boardId") Long boardId,
                              @PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId,
                              @Validated @RequestBody PostCommentUpdateDto dto) {
        // TODO: Member ID
        postCommentService.updateComment(1L, commentId, dto);
    }

    /**
     * 게시글 댓글 삭제
     */
    @DeleteMapping("/{boardId}/posts/{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable("boardId") Long boardId,
                              @PathVariable("postId") Long postId,
                              @PathVariable("commentId") Long commentId) {
        // TODO: Member ID
        postCommentService.deleteComment(1L, commentId);
    }
}
