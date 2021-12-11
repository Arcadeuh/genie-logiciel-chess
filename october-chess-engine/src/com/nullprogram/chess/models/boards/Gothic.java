package com.nullprogram.chess.models.boards;

import com.nullprogram.chess.models.pieces.Archbishop;
import com.nullprogram.chess.models.pieces.Bishop;
import com.nullprogram.chess.models.pieces.Chancellor;
import com.nullprogram.chess.models.pieces.King;
import com.nullprogram.chess.models.pieces.Knight;
import com.nullprogram.chess.models.pieces.Pawn;
import com.nullprogram.chess.models.pieces.Queen;
import com.nullprogram.chess.models.pieces.Rook;
import com.nullprogram.chess.models.Side;

import java.io.Serial;

/**
 * Board for the game of Gothic Chess.
 */
public class Gothic extends StandardBoard {

	/** Serialization identifier. */
	@Serial
    private static final long serialVersionUID = 277220873L;

	/** The standard board width. */
	static final int WIDTH = 10;

	/** The standard board height. */
	static final int HEIGHT = 8;

	/** Row of the white pawns. */
	static final int WHITE_PAWN_ROW = 1;

	/** Row of the black pawns. */
	static final int BLACK_PAWN_ROW = 6;

	/** White home row. */
	static final int WHITE_ROW = 0;

	/** Black home row. */
	static final int BLACK_ROW = 7;

	/** Queen side rook column. */
	static final int Q_ROOK = 0;

	/** Queen side knight column. */
	static final int Q_KNIGHT = 1;

	/** Queen side bishop column. */
	static final int Q_BISHOP = 2;

	/** Queen column. */
	static final int QUEEN = 3;

	/** Chancellor column. */
	static final int CHANCELLOR = 4;

	/** King column. */
	static final int KING = 5;

	/** Archbishop column. */
	static final int ARCHBISHOP = 6;

	/** King side bishop column. */
	static final int K_BISHOP = 7;

	/** King side knight column. */
	static final int K_KNIGHT = 8;

	/** King side rook column. */
	static final int K_ROOK = 9;

	/**
	 * The Gothic Chess board.
	 */
	public Gothic() {
		setWidth(WIDTH);
		setHeight(HEIGHT);
		clear();
		for (int x = 0; x < WIDTH; x++) {
			setPiece(x, WHITE_PAWN_ROW, new Pawn(Side.WHITE));
			setPiece(x, BLACK_PAWN_ROW, new Pawn(Side.BLACK));
		}
		setPiece(Q_ROOK, WHITE_ROW, new Rook(Side.WHITE));
		setPiece(K_ROOK, WHITE_ROW, new Rook(Side.WHITE));
		setPiece(Q_ROOK, BLACK_ROW, new Rook(Side.BLACK));
		setPiece(K_ROOK, BLACK_ROW, new Rook(Side.BLACK));
		setPiece(Q_KNIGHT, WHITE_ROW, new Knight(Side.WHITE));
		setPiece(K_KNIGHT, WHITE_ROW, new Knight(Side.WHITE));
		setPiece(Q_KNIGHT, BLACK_ROW, new Knight(Side.BLACK));
		setPiece(K_KNIGHT, BLACK_ROW, new Knight(Side.BLACK));
		setPiece(Q_BISHOP, WHITE_ROW, new Bishop(Side.WHITE));
		setPiece(K_BISHOP, WHITE_ROW, new Bishop(Side.WHITE));
		setPiece(Q_BISHOP, BLACK_ROW, new Bishop(Side.BLACK));
		setPiece(K_BISHOP, BLACK_ROW, new Bishop(Side.BLACK));
		setPiece(QUEEN, WHITE_ROW, new Queen(Side.WHITE));
		setPiece(QUEEN, BLACK_ROW, new Queen(Side.BLACK));
		setPiece(KING, WHITE_ROW, new King(Side.WHITE));
		setPiece(KING, BLACK_ROW, new King(Side.BLACK));

		setPiece(CHANCELLOR, WHITE_ROW, new Chancellor(Side.WHITE));
		setPiece(CHANCELLOR, BLACK_ROW, new Chancellor(Side.BLACK));
		setPiece(ARCHBISHOP, WHITE_ROW, new Archbishop(Side.WHITE));
		setPiece(ARCHBISHOP, BLACK_ROW, new Archbishop(Side.BLACK));
	}
}
