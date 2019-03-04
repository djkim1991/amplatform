package io.anymobi.repositories.mybatis.mapper.member;


import io.anymobi.domain.dto.users.MemberDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import java.util.List;

@Mapper
public interface MemberMapper {

	List<MemberDto> selectMemberList(MemberDto Member);

	MemberDto selectMember(int id);
	
	void insertMember(MemberDto Member);

	@Insert("INSERT INTO member(email, phoneNo, password) VALUES (#{email}, #{phoneNo}, #{password})")
	@SelectKey(statement = "SELECT currval('member_sequence')", resultType = Long.class, keyProperty = "id", before = false)
	int insert(MemberDto member);

	@Select("SELECT count(1) FROM member")
	long count();

}
