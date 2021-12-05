package practice.mvcstarter.web.controllers.initdb;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.members.Member;
import practice.mvcstarter.domain.members.MemberRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/11/17
 */

@Profile("test")
@Component
@RequiredArgsConstructor
public class InitMember {
    public static final String MEMBER_NAME        = "Spring Integration Test 회원 이름";
    public static final String MEMBER_NICK_NAME   = "Spring Integration Test 회원 닉네임";
    public static final int    MEMBER_TOTAL_COUNT = 40;

    private final InitService initService;

    @PostConstruct
    public void postConstruct() {
        initService.deleteMembers();
        initService.createMembers();
    }

    public List<Member> givenAllMembers() {
        return initService.getMembers();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final MemberRepository memberRepository;

        public List<Member> getMembers() {
            return memberRepository.findAllByNameLike("%" + MEMBER_NAME + "%");
        }

        public void createMembers() {
            IntStream.range(1, MEMBER_TOTAL_COUNT)
                    .forEach(idx -> {
                        String name = MEMBER_NAME + " " + idx;
                        String nickName = MEMBER_NICK_NAME + " " + idx;
                        int age = 10 + idx;
                        memberRepository.save(Member.createMember(name, nickName, age));
                    });
        }

        public void deleteMembers() {
            List<Member> members = memberRepository.findAllByNameLike(MEMBER_NAME);
            memberRepository.deleteAll(members);
        }
    }
}
