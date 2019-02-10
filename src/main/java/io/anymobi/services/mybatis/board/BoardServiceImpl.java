package io.anymobi.services.mybatis.board;

import io.anymobi.domain.dto.board.BoardDto;
import io.anymobi.repositories.mybatis.CommonSqlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardService boardMapper;

    @Autowired
    protected CommonSqlRepository commonSqlRepository;

    @Transactional
    @Override
    public List<BoardDto> selectBoardList(BoardDto BoardDto) {
        return boardMapper.selectBoardList(BoardDto);
    }

    @Transactional
    @Override
    public BoardDto selectBoard(int id){
        return boardMapper.selectBoard(id);
    }

    @Override
    public void insertBoard(BoardDto BoardDto) {
        boardMapper.insertBoard(BoardDto);
    }

    @Transactional
    @Override
    public void updateBoard(BoardDto BoardDto) {
        commonSqlRepository.update("io.anymobi.repositories.mybatis.mapper.board.BoardMapper.updateBoard", BoardDto);
    }

}
