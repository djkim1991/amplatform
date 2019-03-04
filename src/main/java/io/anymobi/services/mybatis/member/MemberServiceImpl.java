package io.anymobi.services.mybatis.member;

import io.anymobi.domain.dto.users.MemberDto;
import io.anymobi.repositories.mybatis.CommonSqlRepository;
import io.anymobi.repositories.mybatis.mapper.member.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl /*extends AbstractMemberService*/ {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    protected CommonSqlRepository commonSqlRepository;

    @Transactional
    /*@Override*/
    public List<MemberDto> selectMemberList(MemberDto memberDto) {
        return memberMapper.selectMemberList(memberDto);
    }

    @Transactional
    /*@Override*/
    public MemberDto selectMember(int id){
        return memberMapper.selectMember(id);
    }

    /*@Override*/
    public void insertMember(MemberDto memberDto) {
        memberMapper.insertMember(memberDto);
    }

    @Transactional
    /*@Override*/
    public void updateMember(MemberDto memberDto) {
        commonSqlRepository.update("io.anymobi.repositories.mybatis.mapper.member.MemberMapper.updateMember", memberDto);
    }

}
