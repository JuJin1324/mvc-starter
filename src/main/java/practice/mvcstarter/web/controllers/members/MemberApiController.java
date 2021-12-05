package practice.mvcstarter.web.controllers.members;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
