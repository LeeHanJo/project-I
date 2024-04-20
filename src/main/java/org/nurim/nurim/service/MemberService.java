package org.nurim.nurim.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.nurim.nurim.domain.dto.member.*;
import org.nurim.nurim.domain.entity.Expert;
import org.nurim.nurim.domain.entity.Member;
import org.nurim.nurim.domain.entity.MemberImage;
import org.nurim.nurim.domain.entity.MemberRole;
import org.nurim.nurim.repository.MemberImageRepository;
import org.nurim.nurim.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberImageRepository memberImageRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    // 일반 회원 가입
    @Transactional
    public CreateMemberResponse createMember(CreateMemberRequest request) {

        // 회원 정보 유효성 검증 (클래스 별도 생성 예정)
        // validateMemberRequest(request);

        if (memberRepository.findMemberByMemberEmail(request.getMemberEmail()).isPresent()) {
            throw new DataIntegrityViolationException("이미 존재하는 회원입니다.");   // 전역예외처리 필요
        }

        // 초기 필드값 default 설정
        Member member = Member.builder()
                .memberEmail(request.getMemberEmail())
                .memberPw(passwordEncoder.encode(request.getMemberPw()))
                .memberNickname(request.getMemberNickname())
                .memberAge(30)
                .gender(true)
                .memberResidence("거주지 주소를 입력해주세요.")
                .memberMarriage(true)
                .memberIncome("소득 정보를 입력해주세요.")
                .type(false)
                .memberRole(MemberRole.USER)
                .build();

        Member savedMember = memberRepository.save(member);

        // 초기 프로필 이미지 URL 설정 (S3 버킷에 저장된 기본 이미지 URL)
        String defaultProfileImageUrl = "https://nurimplus.s3.ap-northeast-2.amazonaws.com/images/c4e11d02-3ed4-4475-9a57-18918721d381.jpeg";
        String defaultKey = "images/c4e11d02-3ed4-4475-9a57-18918721d381.jpeg";

        String defaultExpert = "증빙서류가 등록되지 않았습니다.";

        // 기본 이미지 경로 MemberImage에 설정하여 저장
        MemberImage memberImage = new MemberImage();
        memberImage.setMember(savedMember);
        memberImage.setMemberProfileImage(defaultProfileImageUrl); // 정적 경로 참조
        memberImage.setProfileName(defaultKey);
        memberImageRepository.save(memberImage);

        Expert expert = new Expert();
        expert.setMember(savedMember);
        expert.setExpertFile(defaultExpert);
        expert.setExpertFileName(defaultExpert);

        // 회원 정보에 이미지 정보 연결
        savedMember.setMemberImage(memberImage);
        savedMember.setExpert(expert);
        memberRepository.save(savedMember);

        return new CreateMemberResponse(savedMember.getMemberId(),
                savedMember.getMemberEmail(),
                savedMember.getMemberPw(),
                savedMember.getMemberNickname(),
                savedMember.getMemberAge(),
                savedMember.isGender(),
                savedMember.getMemberResidence(),
                savedMember.isMemberMarriage(),
                savedMember.getMemberIncome(),
                savedMember.isType(),
                savedMember.getMemberRole(),
                savedMember.getMemberImage().getMemberProfileImage(),
                savedMember.getExpert().getExpertFile()
        );

    }

    // 관리자 회원 가입
    @Transactional
    public CreateMemberResponse createAdmin(CreateMemberRequest request) {

        // 회원 정보 유효성 검증 (클래스 별도 생성 예정)
        // validateMemberRequest(request);

        if (memberRepository.findMemberByMemberEmail(request.getMemberEmail()).isPresent()) {
            throw new DataIntegrityViolationException("이미 존재하는 회원입니다.");   // 전역예외처리 필요
        }

        // 초기 필드값 default 설정
        Member member = Member.builder()
                .memberEmail(request.getMemberEmail())
                .memberPw(passwordEncoder.encode(request.getMemberPw()))
                .memberNickname(request.getMemberNickname())
                .memberAge(30)
                .gender(true)
                .memberResidence("서울시 강남구 강남대로 405")
                .memberMarriage(true)
                .memberIncome("해당 없음")
                .type(false)
                .memberRole(MemberRole.ADMIN)
                .build();

        Member savedMember = memberRepository.save(member);

        // 초기 프로필 이미지 URL 설정 (S3 버킷에 저장된 기본 이미지 URL)
        String defaultProfileImageUrl = "https://nurimplus.s3.ap-northeast-2.amazonaws.com/images/c4e11d02-3ed4-4475-9a57-18918721d381.jpeg";
        String defaultKey = "images/c4e11d02-3ed4-4475-9a57-18918721d381.jpeg";

        String defaultExpert = "증빙서류가 등록되지 않았습니다.";

        // 기본 이미지 경로 MemberImage에 설정하여 저장
        MemberImage memberImage = new MemberImage();
        memberImage.setMember(savedMember);
        memberImage.setMemberProfileImage(defaultProfileImageUrl); // 정적 경로 참조
        memberImage.setProfileName(defaultKey);
        memberImageRepository.save(memberImage);

        Expert expert = new Expert();
        expert.setMember(savedMember);
        expert.setExpertFile(defaultExpert);
        expert.setExpertFileName(defaultExpert);

        // 회원 정보에 이미지 정보 연결
        savedMember.setMemberImage(memberImage);
        savedMember.setExpert(expert);
        memberRepository.save(savedMember);

        return new CreateMemberResponse(savedMember.getMemberId(),
                savedMember.getMemberEmail(),
                savedMember.getMemberPw(),
                savedMember.getMemberNickname(),
                savedMember.getMemberAge(),
                savedMember.isGender(),
                savedMember.getMemberResidence(),
                savedMember.isMemberMarriage(),
                savedMember.getMemberIncome(),
                savedMember.isType(),
                savedMember.getMemberRole(),
                savedMember.getMemberImage().getMemberProfileImage(),
                savedMember.getExpert().getExpertFile()
        );

    }

//    // 회원 정보 입력
//    @Transactional
//    public CreateMemberResponse createMemberInfo(CreateMemberInfoRequest request) {
//
//        Member member = Member.builder()
//                .memberEmail(getMember().getMemberEmail())
//                .memberPw(getMember().getMemberPw())
//                .memberNickname(getMember().getMemberNickname())
//                .memberAge(request.getMemberAge())
//                .gender(request.isGender())
//                .memberResidence(request.getMemberResidence())
//                .memberMarriage(request.isMemberMarriage())
//                .memberIncome(request.getMemberIncome())
//                .type(request.isType())
//                .memberRole(getMember().getMemberRole())
//                .build();
//
//        return new CreateMemberResponse(member.getMemberId(),
//                member.getMemberEmail(),
//                member.getMemberPw(),
//                member.getMemberNickname(),
//                member.getMemberAge(),
//                member.isGender(),
//                member.getMemberResidence(),
//                member.isMemberMarriage(),
//                member.getMemberIncome(),
//                member.isType(),
//                member.getMemberRole(),
//                member.getMemberProfileImage());
//
//    }

    // 특정 회원 조회
    public ReadMemberResponse readMemberById(Long memberId) {

        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("😥해당 memberId로 조회된 회원 정보가 없습니다."));

        String profileimageUrl;
        if(foundMember.getMemberImage() != null && foundMember.getMemberImage().getMemberProfileImage() != null) {
            // 프로필 이미지가 등록되어 있는 경우
            profileimageUrl = foundMember.getMemberImage().getMemberProfileImage();
        } else {
            // 프로필 이미지가 등록되지 않은 경우
            profileimageUrl = "https://nurimplus.s3.ap-northeast-2.amazonaws.com/images/c4e11d02-3ed4-4475-9a57-18918721d381.jpeg";
        }

        String expertFileUrl;
        if(foundMember.getExpert() != null && foundMember.getExpert().getExpertFile() != null) {
            // 증빙 서류가 등륵되어 있는 경우
            expertFileUrl = foundMember.getExpert().getExpertFile();
        } else {
            // 증빙 서류가 등록되지 않은 경우
            expertFileUrl = "증빙서류가 등록되지 않았습니다.";
        }

        return new ReadMemberResponse(
                foundMember.getMemberId(),
                foundMember.getMemberEmail(),
                foundMember.getMemberPw(),
                foundMember.getMemberNickname(),
                foundMember.getMemberAge(),
                foundMember.isGender(),
                foundMember.getMemberResidence(),
                foundMember.isMemberMarriage(),
                foundMember.getMemberIncome(),
                foundMember.isType(),
                profileimageUrl,
                expertFileUrl);

    }

    // 특정 회원 정보 수정
    @Transactional
    public UpdateMemberResponse updateMember(Long memberId, UpdateMemberRequest request) {

        // id 확인
        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("😥해당 memberId로 조회된 회원 정보가 없습니다."));

//        // 현재 로그인한 사용자만 수정 가능
//        if(!foundMember.getMemberEmail().equals(getMember().getMemberEmail())) {
//            throw new AccessDeniedException("수정 권한이 없습니다.");
//        }

        // Member 정보 업데이트
        foundMember.update(
                passwordEncoder.encode(request.getMemberPw()),
                request.getMemberNickname(),
                request.getMemberAge(),
                request.isGender(),
                request.getMemberResidence(),
                request.isMemberMarriage(),
                request.getMemberIncome(),
                request.isType());

        return new UpdateMemberResponse(foundMember.getMemberId(),
                foundMember.getMemberEmail(),
                foundMember.getMemberPw(),
                foundMember.getMemberNickname(),
                foundMember.getMemberAge(),
                foundMember.isGender(),
                foundMember.getMemberResidence(),
                foundMember.isMemberMarriage(),
                foundMember.getMemberIncome(),
                foundMember.isType(),
                foundMember.getMemberImage().getMemberProfileImage(),
                foundMember.getExpert().getExpertFile());

    }

//    // 회원 탈퇴
//    @Transactional
//    public DeleteMemberResponse deleteMember(Long memberId) {
//
//        Member foundMember = memberRepository.findById(memberId)
//                .orElseThrow(() -> new EntityNotFoundException("😥해당 memberId로 조회된 회원 정보가 없습니다."));
//
////        // 현재 로그인한 사용자만 탈퇴 가능
////        if(!foundMember.getMemberEmail().equals(getMember().getMemberEmail())) {
////            throw new AccessDeniedException("이 계정 탈퇴에 대한 권한이 없습니다.");
////        }
//
//        memberRepository.delete(foundMember);
//
//        return new DeleteMemberResponse(foundMember.getMemberId());
//
//    }


    // context에서 회원정보 가져오기

    public Member readMemberByMemberEmail(String username) {
        Member foundMember = memberRepository.findMemberByMemberEmail(username)
                .orElseThrow(() -> new EntityNotFoundException("😥해당 이메일로 회원을 찾을 수 없습니다."));

        return foundMember;
    }

    public Member getMemberById(Long memberId) {

        Member foundMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 memberId로 회원을 찾을 수 없습니다."));

        log.info("😀"+foundMember);

        return foundMember;
    }

    public boolean isCurrentUser(Long memberId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false; // 로그인한 사용자가 없는 경우
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String currentUsername = userDetails.getUsername();

        // 현재 로그인한 사용자의 username과 memberId에 해당하는 회원의 username이 일치하는지 확인
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        return memberOptional.isPresent() && memberOptional.get().getMemberEmail().equals(currentUsername);
    }

}