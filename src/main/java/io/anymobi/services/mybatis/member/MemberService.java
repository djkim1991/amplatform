package io.anymobi.services.mybatis.member;

import io.anymobi.domain.dto.users.MemberDto;

import java.util.List;


/**
 * <PRE>
 * 1. author	:	(주)애니모비 시스템 개발본부
 * 2. date		:	2019.01
 * </PRE>
 *
 */

public interface MemberService {
	
	List<MemberDto> selectMemberList(MemberDto memberDto) ;

	MemberDto selectMember(int id) ;
	
	void insertMember(MemberDto memberDto) ;
	
	void updateMember(MemberDto memberDto);

	MemberDto join(MemberDto memberDto);
}
