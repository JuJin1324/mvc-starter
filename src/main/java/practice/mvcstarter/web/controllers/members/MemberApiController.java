package practice.mvcstarter.web.controllers.members;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mvcstarter.domain.files.ContentType;
import practice.mvcstarter.domain.members.MemberDto;
import practice.mvcstarter.domain.members.MemberService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    public Long createMember(@Validated @RequestBody CreateMemberReqBody reqBody) {
        return memberService.createMember(reqBody.toDto());
    }

    /**
     * 회원 조회 - 단건
     */
    @GetMapping("/{memberId}")
    public GetSingleMemberResBody getSingleMember(@PathVariable("memberId") Long memberId) {
        return GetSingleMemberResBody.createReqBody(memberService.getSingleMember(memberId));
    }

    /**
     * 회원 갱신
     */
    @PutMapping("/{memberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMember(@PathVariable("memberId") Long memberId,
                             @Validated @RequestBody UpdateMemberReqBody reqBody) {
        memberService.updateMember(memberId, reqBody.toDto());
    }

    /**
     * 회원 갱신 - 프로필
     */
    @PutMapping("/{memberId}/profile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateMemberProfile(@PathVariable("memberId") Long memberId,
                                    @Validated @RequestBody UpdateMemberProfileReqBody reqBody) {
        memberService.updateMemberProfile(memberId, reqBody.toDto());
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
     * 관심 게시글 조회 - 목록
     */
    @GetMapping("/{memberId}/boards/attractive")
    public GetAttractiveBoardsResBody getAttractiveBoards(@PathVariable("memberId") Long memberId) {
        return null;
    }

    /**
     * 내가 쓴 게시글 조회 - 목록
     */
    @GetMapping("/{memberId}/boards/mine")
    public GetMineBoardsResBody getMineBoards(@PathVariable("memberId") Long memberId) {
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


    @Data
    static class CreateMemberReqBody {
        @NotBlank
        private String  name;
        @NotBlank
        private String  nickName;
        @NotNull
        private Integer age;

        public MemberDto toDto() {
            return MemberDto.toCreate(name, nickName, age);
        }
    }

    @Data
    static class UpdateMemberReqBody {
        @NotBlank
        private String  nickName;
        @NotNull
        private Integer age;

        public MemberDto toDto() {
            return MemberDto.toUpdate(nickName, age);
        }
    }

    @Data
    static class UpdateMemberProfileReqBody {
        private String      base64Image;
        private ContentType contentType;

        public MemberDto toDto() {
            return MemberDto.toUpdateProfile(base64Image, contentType);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class GetSingleMemberResBody {
        private Long    memberId;
        private String  name;
        private String  nickName;
        private Integer age;
        private String  contentType;
        private String  base64Image;

        public static GetSingleMemberResBody createReqBody(MemberDto memberDto) {
            return new GetSingleMemberResBody(
                    memberDto.getMemberId(),
                    memberDto.getName(),
                    memberDto.getNickName(),
                    memberDto.getAge(),
                    memberDto.getContentType() != null ? memberDto.getContentType().getValue() : null,
                    memberDto.getBase64Image()
            );
        }
    }

    @Data
    @AllArgsConstructor
    static class GetAttractiveBoardsResBody {
    }

    @Data
    @AllArgsConstructor
    static class GetMineBoardsResBody {

    }
}
