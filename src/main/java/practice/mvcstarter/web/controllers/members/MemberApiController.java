package practice.mvcstarter.web.controllers.members;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import practice.mvcstarter.domain.members.MemberDto;
import practice.mvcstarter.domain.members.MemberService;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {
    private final MemberService memberService;

    /**
     * 회원 생성
     */
    @PostMapping("")
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
     * 회원 조회 - 페이지
     */
    @GetMapping("")
    public Page<GetSingleMemberResBody> getMemberPage(@PageableDefault(size = 20) Pageable pageable) {
        return memberService.getMemberPage(pageable)
                .map(GetSingleMemberResBody::createReqBody);
    }

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
    @NoArgsConstructor
    @AllArgsConstructor
    static class GetSingleMemberResBody {
        private Long    memberId;
        private String  name;
        private String  nickName;
        private Integer age;

        public static GetSingleMemberResBody createReqBody(MemberDto memberDto) {
            return new GetSingleMemberResBody(
                    memberDto.getMemberId(),
                    memberDto.getName(),
                    memberDto.getNickName(),
                    memberDto.getAge()
            );
        }
    }
}
