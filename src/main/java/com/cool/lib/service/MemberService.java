package com.cool.lib.service;

import com.cool.lib.domain.Member;
import com.cool.lib.repository.MemberRepository;
import com.cool.lib.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    /**
     * 회원가입
     * @param member
     * @return
     */
    public Long join(Member member){
        //같은 이름이 있는 중복회원은 X
//        Optional<Member> result = memberRepository.findByName(member.getName());
//        result.ifPresent(m->{
//            throw new IllegalStateException("이미 존재하는 회원입니다");
//        });
        validateDupuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDupuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m->{
                       throw  new IllegalStateException("이미 존재하는 회원입니다");
        });
    }

    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
