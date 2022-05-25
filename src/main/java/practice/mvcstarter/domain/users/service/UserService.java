package practice.mvcstarter.domain.users.service;

import practice.mvcstarter.domain.users.dto.UserCreateDto;
import practice.mvcstarter.domain.users.dto.UserReadDto;
import practice.mvcstarter.domain.users.dto.UserUpdateDto;
import practice.mvcstarter.domain.users.dto.UserUpdateProfileDto;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

public interface UserService {
    /**
     * 회원 생성
     *
     * @return userId
     */
    Long createUser(UserCreateDto dto);

    /**
     * 회원 조회 - 단건
     */
    UserReadDto getSingleUser(Long memberId);

    /**
     * 회원 갱신
     */
    void updateUser(Long memberId, UserUpdateDto dto);

    /**
     * 회원 갱신 - 프로필
     */
    void updateProfile(Long memberId, UserUpdateProfileDto dto);

    /**
     * 회원 삭제
     */
    void deleteUser(Long memberId);
}
