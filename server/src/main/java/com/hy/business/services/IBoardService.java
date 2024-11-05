package com.hy.business.services;

import com.hy.business.entity.Board;

import java.io.IOException;
import java.util.List;

public interface IBoardService {
    public List<Board> GetBoards() throws IOException;
}
