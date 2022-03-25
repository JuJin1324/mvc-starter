package practice.mvcstarter.domain.users.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.files.entity.FileStore;
import practice.mvcstarter.domain.files.service.FileService;
import practice.mvcstarter.domain.users.dto.UserCreateDto;
import practice.mvcstarter.domain.users.dto.UserUpdateDto;
import practice.mvcstarter.domain.users.dto.UserUpdateProfileDto;
import practice.mvcstarter.domain.users.entity.User;
import practice.mvcstarter.domain.users.exception.UserNotFoundException;
import practice.mvcstarter.domain.users.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Created by Yoo Ju Jin(jujin1324@daum.net)
 * Created Date : 2021/12/05
 */

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final Long   MEMBER_ID_VALID     = 1L;
    private static final Long   MEMBER_ID_NOT_EXIST = 999999999L;
    private static final String MEMBER_NAME         = "회원 이름";
    private static final String MEMBER_NICK_NAME    = "회원 닉네임";
    private static final int    MEMBER_AGE          = 20;

    private static final String MEMBER_NICK_NAME_NEW = "새로운 회원 이름";
    private static final int    MEMBER_AGE_NEW       = 30;

    private static final String      BASE64_IMAGE     = "iVBORw0KGgoAAAANSUhEUgAAAAgAAAAKCAYAAACJxx+AAAABYWlDQ1BrQ0dDb2xvclNwYWNlRGlzcGxheVAzAAAokWNgYFJJLCjIYWFgYMjNKykKcndSiIiMUmB/yMAOhLwMYgwKicnFBY4BAT5AJQwwGhV8u8bACKIv64LMOiU1tUm1XsDXYqbw1YuvRJsw1aMArpTU4mQg/QeIU5MLikoYGBhTgGzl8pICELsDyBYpAjoKyJ4DYqdD2BtA7CQI+whYTUiQM5B9A8hWSM5IBJrB+API1klCEk9HYkPtBQFul8zigpzESoUAYwKuJQOUpFaUgGjn/ILKosz0jBIFR2AopSp45iXr6SgYGRiaMzCAwhyi+nMgOCwZxc4gxJrvMzDY7v////9uhJjXfgaGjUCdXDsRYhoWDAyC3AwMJ3YWJBYlgoWYgZgpLY2B4dNyBgbeSAYG4QtAPdHFacZGYHlGHicGBtZ7//9/VmNgYJ/MwPB3wv//vxf9//93MVDzHQaGA3kAFSFl7jXH0fsAAACKZVhJZk1NACoAAAAIAAQBGgAFAAAAAQAAAD4BGwAFAAAAAQAAAEYBKAADAAAAAQACAACHaQAEAAAAAQAAAE4AAAAAAAAAkAAAAAEAAACQAAAAAQADkoYABwAAABIAAAB4oAIABAAAAAEAAAAIoAMABAAAAAEAAAAKAAAAAEFTQ0lJAAAAU2NyZWVuc2hvdPoE6XgAAAAJcEhZcwAAFiUAABYlAUlSJPAAAAHTaVRYdFhNTDpjb20uYWRvYmUueG1wAAAAAAA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJYTVAgQ29yZSA2LjAuMCI+CiAgIDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+CiAgICAgIDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiCiAgICAgICAgICAgIHhtbG5zOmV4aWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20vZXhpZi8xLjAvIj4KICAgICAgICAgPGV4aWY6UGl4ZWxZRGltZW5zaW9uPjEwPC9leGlmOlBpeGVsWURpbWVuc2lvbj4KICAgICAgICAgPGV4aWY6UGl4ZWxYRGltZW5zaW9uPjg8L2V4aWY6UGl4ZWxYRGltZW5zaW9uPgogICAgICAgICA8ZXhpZjpVc2VyQ29tbWVudD5TY3JlZW5zaG90PC9leGlmOlVzZXJDb21tZW50PgogICAgICA8L3JkZjpEZXNjcmlwdGlvbj4KICAgPC9yZGY6UkRGPgo8L3g6eG1wbWV0YT4KbJxrmQAAABxpRE9UAAAAAgAAAAAAAAAFAAAAKAAAAAUAAAAFAAAAStvdOSgAAAAWSURBVCgVYmRgYPgPxDgBI1BmoBUAAAAA//8vs7vuAAAAFElEQVRjZGBg+A/EOAEjUGagFQAA8MwKAQlk/YkAAAAASUVORK5CYII=";
    private static final ContentType CONTENT_TYPE_PNG = ContentType.IMAGE_PNG;

    @Mock
    private UserRepository userRepository;
    @Mock
    private FileService    fileService;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, fileService);
    }

    @Test
    @DisplayName("[회원 생성] 1.유효하지 않은 매개변수")
    void createMember_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> userService.createMember(null));

        /* 이름 blank */
        assertThrows(IllegalArgumentException.class, () ->
                userService.createMember(new UserCreateDto(null, null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                userService.createMember(new UserCreateDto("", null, null)));

        /* Name 이 blank */
        assertThrows(IllegalArgumentException.class, () ->
                userService.createMember(new UserCreateDto(null, null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                userService.createMember(new UserCreateDto("", null, null)));

        /* NickName 이 blank */
        assertThrows(IllegalArgumentException.class, () ->
                userService.createMember(new UserCreateDto(MEMBER_NAME, null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                userService.createMember(new UserCreateDto(MEMBER_NAME, "", null)));

        /* age 가 null */
        assertThrows(IllegalArgumentException.class, () ->
                userService.createMember(new UserCreateDto(MEMBER_NAME, MEMBER_NICK_NAME, null)));
    }

    @Test
    @DisplayName("[회원 생성] 2.정상 생성")
    void createMember_whenNormal_thenReturnMemberId() {
        /* given */
        UserCreateDto givenDto = new UserCreateDto(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);

        /* when */
        userService.createMember(givenDto);

        /* then */
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("[회원 조회 - 단건] 1.유효하지 않은 매개변수")
    void getSingleMember_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> userService.getSingleUser(null));
    }

    @Test
    @DisplayName("[회원 조회 - 단건] 2.존재하지 않는 memberId")
    void getSingleMember_whenNotExistMemberId_thenThrowException() {
        /* given */
        when(userRepository.findWithMemberFilesById(MEMBER_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(UserNotFoundException.class, () ->
                userService.getSingleUser(MEMBER_ID_NOT_EXIST));
    }

    @Test
    @DisplayName("[회원 갱신] 1.유효하지 않은 매개변수")
    void updateMember_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateUser(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateUser(MEMBER_ID_VALID, null));

        assertThrows(IllegalArgumentException.class, () ->
                userService.updateUser(MEMBER_ID_VALID, new UserUpdateDto(null, null)));
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateUser(MEMBER_ID_VALID, new UserUpdateDto("", null)));
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateUser(MEMBER_ID_VALID, new UserUpdateDto(MEMBER_NICK_NAME_NEW, null)));
    }

    @Test
    @DisplayName("[회원 갱신] 2.존재하지 않는 memberId")
    void updateMember_whenNotExistMemberId_thenThrowException() {
        /* given */
        when(userRepository.findById(MEMBER_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(UserNotFoundException.class, () -> {
            UserUpdateDto givenDto = new UserUpdateDto(MEMBER_NICK_NAME_NEW, MEMBER_AGE_NEW);
            userService.updateUser(MEMBER_ID_NOT_EXIST, givenDto);
        });
    }

    @Test
    @DisplayName("[회원 갱신] 3.정상 갱신")
    void updateMember_whenNormal_thenUpdateTeam() {
        /* given */
        User givenUser = User.createMember(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);
        when(userRepository.findById(MEMBER_ID_VALID))
                .thenReturn(Optional.of(givenUser));

        /* when */
        UserUpdateDto givenDto = new UserUpdateDto(MEMBER_NICK_NAME_NEW, MEMBER_AGE_NEW);
        userService.updateUser(MEMBER_ID_VALID, givenDto);

        /* then */
        assertThat(givenUser.getNickName()).isEqualTo(MEMBER_NICK_NAME_NEW);
        assertThat(givenUser.getAge()).isEqualTo(MEMBER_AGE_NEW);
    }

    @Test
    @DisplayName("[회원 갱신 - 프로필] 1.유효하지 않은 매개변수")
    void updateMemberProfile_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateProfile(null, null));
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateProfile(MEMBER_ID_VALID, null));
        assertThrows(IllegalArgumentException.class, () ->
                userService.updateProfile(MEMBER_ID_VALID, new UserUpdateProfileDto(BASE64_IMAGE, null)));
    }

    @Test
    @DisplayName("[회원 갱신 - 프로필] 2.존재하지 않는 memberId")
    void updateMemberProfile_whenNotExistMemberId_thenThrowException() {
        /* given */
        UserUpdateProfileDto givenDto = new UserUpdateProfileDto(BASE64_IMAGE, CONTENT_TYPE_PNG);
        when(userRepository.findWithMemberFilesById(MEMBER_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(UserNotFoundException.class, () ->
                userService.updateProfile(MEMBER_ID_NOT_EXIST, givenDto));
    }

    @Test
    @DisplayName("[회원 갱신 - 프로필] 3.input dto 에 base64 image 가 있는 경우")
    void updateMemberProfile_whenDtoHasBase64_thenMemberHasNewProfileFile() {
        /* given */
        User givenUser = User.createMember(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);
        when(userRepository.findWithMemberFilesById(MEMBER_ID_VALID))
                .thenReturn(Optional.of(givenUser));

        FileStore givenFileStore = FileStore.createFile(ContentType.IMAGE_PNG, "/home/files/image.png", "image.png", 10L);
        when(fileService.uploadBase64(any(), any(), any()))
                .thenReturn(givenFileStore);

        /* when */
        UserUpdateProfileDto givenDto = new UserUpdateProfileDto(BASE64_IMAGE, CONTENT_TYPE_PNG);
        userService.updateProfile(MEMBER_ID_VALID, givenDto);

        /* then */
        assertThat(givenUser.getProfile().get()).isEqualTo(givenFileStore);
    }

    @Test
    @DisplayName("[회원 갱신 - 프로필] 4.input dto 에 base64 image 가 없는 경우")
    void updateMemberProfile_whenDtoHasNoBase64_thenMemberHasNoProfile() {
        /* given */
        User givenUser = User.createMember(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);
        when(userRepository.findWithMemberFilesById(MEMBER_ID_VALID))
                .thenReturn(Optional.of(givenUser));

        /* when */
        UserUpdateProfileDto givenDto = new UserUpdateProfileDto(null, null);
        userService.updateProfile(MEMBER_ID_VALID, givenDto);

        /* then */
        assertThat(givenUser.getProfile()).isEmpty();
    }

    @Test
    @DisplayName("[회원 삭제] 1.유효하지 않은 매개변수")
    void deleteMember_whenInvalidParam_thenThrowException() {
        assertThrows(IllegalArgumentException.class, () ->
                userService.deleteUser(null));
    }

    @Test
    @DisplayName("[회원 삭제] 2.존재하지 않는 memberId")
    void deleteMember_whenNotExistMemberId_thenThrowException() {
        /* given */
        when(userRepository.findWithMemberFilesById(MEMBER_ID_NOT_EXIST))
                .thenReturn(Optional.empty());

        /* when, then */
        assertThrows(UserNotFoundException.class, () ->
                userService.deleteUser(MEMBER_ID_NOT_EXIST));
    }

    @Test
    @DisplayName("[회원 삭제] 3.정상 삭제")
    void deleteMember_whenNormal_thenUpdateTeam() {
        /* given */
        User givenUser = User.createMember(MEMBER_NAME, MEMBER_NICK_NAME, MEMBER_AGE);
        when(userRepository.findWithMemberFilesById(MEMBER_ID_VALID))
                .thenReturn(Optional.of(givenUser));

        /* when */
        userService.deleteUser(MEMBER_ID_VALID);

        /* then */
        verify(userRepository, times(1)).delete(givenUser);
    }
}