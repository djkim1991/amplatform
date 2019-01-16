package io.anymobi.repositories.mybatis.mapper.member;


import io.anymobi.domain.dto.security.MemberDto;

import java.util.List;

public interface MemberMapper {

	List<MemberDto> selectMemberList(MemberDto Member);

	MemberDto selectMember(int id);
	
	void insertMember(MemberDto Member);

}
