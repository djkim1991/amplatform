package io.anymobi.services.mybatis.member;

import io.anymobi.domain.dto.security.MemberDto;
import io.anymobi.repositories.mybatis.mapper.member.MemberMapper;
import io.anymobi.services.mybatis.AbstractBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MemberServiceImpl extends AbstractBaseService<MemberMapper> implements MemberService {

    @Transactional
    @Override
    public List<MemberDto> selectMemberList(MemberDto memberDto) {
        MemberMapper mapper = getMapper(MemberMapper.class);
        return mapper.selectMemberList(memberDto);
    }

    @Transactional
    @Override
    public MemberDto selectMember(int id){
        MemberMapper mapper = getMapper(MemberMapper.class);
        return mapper.selectMember(id);
    }

    @Override
    public void insertMember(MemberDto memberDto) {
        MemberMapper mapper = getMapper(MemberMapper.class);
        mapper.insertMember(memberDto);
    }

    @Transactional
    @Override
    public void updateMember(MemberDto memberDto) {
        commonSqlRepository.update("io.anymobi.repositories.mybatis.mapper.member.MemberMapper.updateMember", memberDto);
    }

}
