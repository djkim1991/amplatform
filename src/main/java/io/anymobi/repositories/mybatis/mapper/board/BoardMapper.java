package io.anymobi.repositories.mybatis.mapper.board;


import io.anymobi.domain.dto.board.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

	List<BoardDto> selectBoardList(BoardDto boardDto);

	BoardDto selectBoard(long idx);
	
	void insertBoard(BoardDto boardDto);

}
