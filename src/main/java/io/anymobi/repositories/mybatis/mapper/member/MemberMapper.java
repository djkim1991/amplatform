package io.anymobi.repositories.mybatis.mapper.member;


import io.anymobi.domain.dto.security.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

	List<MemberDto> selectMemberList(MemberDto Member);

	MemberDto selectMember(int id);
	
	void insertMember(MemberDto Member);

}
