package app.askresume.api.member.facade;

import app.askresume.domain.member.entity.Member;
import app.askresume.domain.member.service.MemberService;
import app.askresume.api.member.dto.request.ModifyInfoRequest;
import app.askresume.api.member.dto.response.MemberInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyMemberFacade {

    private final MemberService memberService;


    public MemberInfoResponse findMemberInfo(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        return MemberInfoResponse.of(member);
    }

    @Transactional
    public void modifyMemberInfo(Long memberId, ModifyInfoRequest request) {
        Member member = memberService.findMemberById(memberId);
        memberService.modifyMemberInfo(member, request);
    }

    @Transactional
    public void secessionMember(Long memberId) {
        Member member = memberService.findMemberById(memberId);
        memberService.secessionMember(member);
    }
}
