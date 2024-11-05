package com.hy.business.services.impl;

import com.hy.business.dao.BoardMapper;
import com.hy.business.entity.Board;
import com.hy.business.services.IBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class BoardServiceImpl implements IBoardService {

    @Autowired
    BoardMapper boardMapper;

    @Override
    public List<Board> GetBoards() throws IOException {
        return boardMapper.selectList(null);
    }
}
