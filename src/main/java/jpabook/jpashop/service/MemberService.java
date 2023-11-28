package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

    /**
     * 생성자 인젝션(의존성 주입 시 생성 좋은 방법) 생성자를 통해 받고 필드에 있는 리포지토리에 셋 해줌
     * 하지만 @RequiredArgsConstructor 해당 어노테이션 사용 시 final로 의존관계맺을 것들을
     * 자동 생성자 주입 해줌, final이 아닐시는 @AllArgsConstructor사용으로 생성자 자동주입 사용
     */
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    // 회원 가입
    @Transactional
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }


    // 중복회원 검증
    private void validateDuplicateMember(Member member) {
        // exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존해하는 회원입니다.");
        }
    }

    // 회원 전체 조회
    public List<Member> findMebers(){
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
