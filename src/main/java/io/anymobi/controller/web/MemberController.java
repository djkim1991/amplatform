package io.anymobi.controller.web;

import io.anymobi.domain.dto.security.MemberDto;
import io.anymobi.services.mybatis.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * <PRE>
 * 1. author	:	(주)애니모비 시스템 개발본부
 * 2. date		:	2019.01
 * </PRE>
 *
 */
@Controller
@Slf4j
public class MemberController {

	@Autowired
	MemberService memberService;

	@GetMapping(value="/members/home")
	public String members() throws Exception {

		return "test/index";
	}

	@GetMapping(value="/members/join")
	public String memberJoin() throws Exception {

		return "test/member";
	}
	
	@PostMapping(value="/members")
	public String registerMember(MemberDto member) throws Exception {

		memberService.insertMember(member);
		return "test/index";
		
	}
	
	@GetMapping(value="/members/{id}")
	public MemberDto selectMember(@PathVariable int id) throws Exception {

		MemberDto Member = memberService.selectMember(id);
		return Member;
	}

	@GetMapping(value="/members")
	public List<MemberDto> selectMemberList(MemberDto member) throws Exception {
		
		return memberService.selectMemberList(member);
	}

	@GetMapping(value="/members/edit")
	public String memberEdit() throws Exception {

		return "test/member_edit";
	}

	@PostMapping(value="/members/{id}")
	public String editMember(@PathVariable int id, MemberDto memberDto) throws Exception {
		memberDto.setId(id);
		memberService.updateMember(memberDto);
		return "test/index";
	}
}
