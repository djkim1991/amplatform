package io.anymobi.services.mybatis.member;

import io.anymobi.domain.dto.users.MemberDto;

import java.util.List;

abstract class AbstractMemberService implements MemberService {

    @Override
    public List<MemberDto> selectMemberList(MemberDto memberDto) {
        return null;
    }
    @Override
    public MemberDto selectMember(int id) {
        return null;
    }
    @Override
    public void insertMember(MemberDto memberDto) { }
    @Override
    public void updateMember(MemberDto memberDto) { }
    @Override
    public MemberDto join(MemberDto memberDto) {
        return null;
    }
}
