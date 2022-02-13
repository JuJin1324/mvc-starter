package practice.mvcstarter.domain.members;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.mvcstarter.domain.files.ContentType;
import practice.mvcstarter.domain.files.File;
import practice.mvcstarter.domain.files.FileDto;
import practice.mvcstarter.domain.files.FileService;
import practice.mvcstarter.exceptions.ResourceNotFoundException;

import java.util.Optional;

/**
 * Created by Yoo Ju Jin(jujin@100fac.com)
 * Created Date : 2022/02/11
 * Copyright (C) 2022, Centum Factorial all rights reserved.
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    public static final String RESOURCE_NAME = "Member";

    private final MemberRepository memberRepository;
    private final FileService      fileService;

    /**
     * 회원 생성
     *
     * @return memberId
     */
    @Transactional
    public Long createMember(MemberDto dto) {
        if (dto == null) {
            throw new IllegalArgumentException("toCreate Dto is null.");
        }
        dto.validateToCreate();

        Member member = Member.createMember(dto.getName(), dto.getNickName(), dto.getAge());
        memberRepository.save(member);

        return member.getId();
    }

    /**
     * 회원 조회 - 단건
     */
    public MemberDto getSingleMember(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }

        Member member = memberRepository.findWithMemberFilesById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME));

        Optional<FileDto> fileDtoOptional = member.getProfile()
                .map(file -> fileService.getFile(file.getId()));

        return MemberDto.toRead(member, fileDtoOptional);
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
    public void updateMember(Long memberId, MemberDto dto) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }
        if (dto == null) {
            throw new IllegalArgumentException("toUpdate Dto is null.");
        }
        dto.validateToUpdate();

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME));
        member.update(dto.getNickName(), dto.getAge());
    }

    /**
     * 회원 갱신 - 프로필
     */
    @Transactional
    @Override
    public void updateMemberProfile(Long memberId, MemberDto dto) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }
        if (dto == null) {
            throw new IllegalArgumentException("toUpdate Dto is null.");
        }
        dto.validateToUpdateProfile();

        Member member = memberRepository.findWithMemberFilesById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME));
        member.getProfile()
                .ifPresent(file -> fileService.deleteFile(file.getId()));

        if (dto.hasBase64Image()) {
            String uploadFileName = member.getNickName() + "님의 프로필" + "." + ContentType.getExtension(dto.getContentType());
            File file = fileService.uploadBase64(uploadFileName, dto.getContentType(), dto.getBase64Image());
            member.updateProfile(file);
        }
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMember(Long memberId) {
        if (memberId == null) {
            throw new IllegalArgumentException("memberId is null.");
        }

        Member member = memberRepository.findWithMemberFilesById(memberId)
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NAME));
        member.getProfile()
                .ifPresent(file -> fileService.deleteFile(file.getId()));
        memberRepository.delete(member);
    }
}
