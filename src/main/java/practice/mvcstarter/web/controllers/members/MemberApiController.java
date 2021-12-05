package practice.mvcstarter.web.controllers.members;

import lombok.*;
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
     * 회원 조회 단건
     */
    @GetMapping("/{memberId}")
    public GetSingleMemberResBody getSingleMember(@PathVariable("memberId") Long memberId) {
        MemberDto member = memberService.getSingleMember(memberId);

        return new GetSingleMemberResBody(
                member.getMemberId(),
                member.getName(),
                member.getNickName(),
                member.getAge()
        );
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
    }
}
