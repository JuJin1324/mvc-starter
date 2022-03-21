package practice.mvcstarter.domain.users.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.files.dto.FileBase64ReadDto;
import practice.mvcstarter.domain.files.entity.ContentType;
import practice.mvcstarter.domain.files.entity.FileStore;
import practice.mvcstarter.domain.files.service.FileService;
import practice.mvcstarter.domain.users.dto.UserCreateDto;
import practice.mvcstarter.domain.users.dto.UserReadDto;
import practice.mvcstarter.domain.users.dto.UserUpdateDto;
import practice.mvcstarter.domain.users.dto.UserUpdateProfileDto;
import practice.mvcstarter.domain.users.entity.User;
import practice.mvcstarter.domain.users.exception.UserNotFoundException;
import practice.mvcstarter.domain.users.repository.UserRepository;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/11
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileService    fileService;

    /**
     * 회원 생성
     *
     * @return memberId
     */
    @Transactional
    public Long createMember(UserCreateDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("toCreate Dto is null.");
        }
        dto.validate();

        User user = User.createMember(dto.getName(), dto.getNickName(), dto.getAge());
        userRepository.save(user);

        return user.getId();
    }

    /**
     * 회원 조회 - 단건
     */
    public UserReadDto getSingleUser(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }

        User user = userRepository.findWithMemberFilesById(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));

        Optional<FileBase64ReadDto> profileOptional = user.getProfile()
                .map(file -> fileService.getFileBase64(file.getId()));

        return new UserReadDto(user, profileOptional);
    }

    /**
     * 회원 조회 - 페이지
     */
//    public Page<MemberDto> getMemberPage(Pageable pageable) {
//        return memberRepository.findAll(pageable)
//                .map(MemberDto::toRead);
//    }

    /**
     * 회원 갱신
     */
    @Transactional
    public void updateUser(Long memberId, UserUpdateDto dto) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }
        if (dto == null) {
            throw new IllegalArgumentException("toUpdate Dto is null.");
        }
        dto.validate();

        User user = userRepository.findById(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));
        user.update(dto.getNickName(), dto.getAge());
    }

    /**
     * 회원 갱신 - 프로필
     */
    @Transactional
    @Override
    public void updateProfile(Long memberId, UserUpdateProfileDto dto) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }
        if (dto == null) {
            throw new IllegalArgumentException("toUpdate Dto is null.");
        }
        dto.validate();

        User user = userRepository.findWithMemberFilesById(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));
        user.getProfile()
                .ifPresent(file -> fileService.deleteFile(file.getId()));

        if (dto.hasBase64Image()) {
            String uploadFileName = user.getNickName() + "님의 프로필" + "." + ContentType.getExtension(dto.getContentType());
            FileStore fileStore = fileService.uploadBase64(uploadFileName, dto.getContentType(), dto.getBase64Image());
            user.updateProfile(fileStore);
        }
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteUser(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }

        User user = userRepository.findWithMemberFilesById(memberId)
                .orElseThrow(() -> new UserNotFoundException(memberId));
        user.getProfile()
                .ifPresent(file -> fileService.deleteFile(file.getId()));
        userRepository.delete(user);
    }
}
