package com.nullprogram.chess;

import com.nullprogram.chess.models.Board;
import com.nullprogram.chess.models.boards.EmptyBoard;
//import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import static org.junit.Assert.assertEquals;

public class BoardTest {

    private static final int SIZE = 8;
    private static final Board board = new EmptyBoard(SIZE, SIZE);

    @Test
    public void testGetWidth() {
        Assertions.assertEquals(SIZE, board.getWidth());
    }

    @Test
    public void testGetHeight() {
        Assertions.assertEquals(SIZE, board.getWidth());
    }
}
