package io.anymobi.services.mybatis.board;

import io.anymobi.domain.dto.board.BoardDto;
import io.anymobi.domain.dto.security.MemberDto;

import java.util.List;


/**
 * <PRE>
 * 1. author	:	(주)애니모비 시스템 개발본부
 * 2. date		:	2019.01
 * </PRE>
 *
 */

public interface BoardService {
	
	List<BoardDto> selectBoardList(BoardDto boardDto) ;

	BoardDto selectBoard(long idx) ;
	
	void insertBoard(BoardDto boardDto) ;
	
	void updateBoard(BoardDto boardDto);
}
