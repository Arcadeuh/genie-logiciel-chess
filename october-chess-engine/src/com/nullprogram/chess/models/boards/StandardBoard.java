package com.nullprogram.chess.models.boards;

import com.nullprogram.chess.models.Board;
import com.nullprogram.chess.models.Piece;
import com.nullprogram.chess.models.Position;
import com.nullprogram.chess.models.Side;
import com.nullprogram.chess.models.pieces.Bishop;
import com.nullprogram.chess.models.pieces.King;
import com.nullprogram.chess.models.pieces.Knight;
import com.nullprogram.chess.models.pieces.Pawn;
import com.nullprogram.chess.models.pieces.Queen;
import com.nullprogram.chess.models.pieces.Rook;

import java.io.Serial;

/**
 * The board for a standard game of chess.
 */
public class StandardBoard extends Board {

	/** Serialization identifier. */
	@Serial
    private static final long serialVersionUID = -484123716L;

	/** The standard board width. */
	static final int WIDTH = 8;

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

	/** King column. */
	static final int KING = 4;

	/** King side bishop column. */
	static final int K_BISHOP = 5;

	/** King side knight column. */
	static final int K_KNIGHT = 6;

	/** King side rook column. */
	static final int K_ROOK = 7;

	/**
	 * The standard chess board.
	 */
	public StandardBoard() {
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
	}

	@Override
	public final Boolean checkmate(final Side side) {
		return check(side) && (moveCount(side) == 0);
	}

	@Override
	public final Boolean stalemate(final Side side) {
		return (!check(side)) && (moveCount(side) == 0);
	}

	/**
	 * Number of moves available to this player.
	 *
	 * @param side side to be tested
	 * @return number of moves right now
	 */
	public final int moveCount(final Side side) {
		int count = 0;
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				Piece p = getPiece(new Position(x, y));
				if ((p != null) && (p.getSide() == side)) {
					count += p.getMoves(true).size();
				}
			}
		}
		return count;
	}

	@Override
	public final Boolean check(final Side side) {
		Side attacker;
		if (side == Side.WHITE) {
			attacker = Side.BLACK;
		} else {
			attacker = Side.WHITE;
		}
		Position kingPos = findKing(side);
		if (kingPos == null) {
			/* no king on board, but can happen in AI evaluation */
			return false;
		}
		for (int y = 0; y < getHeight(); y++) {
			for (int x = 0; x < getWidth(); x++) {
				Piece p = getPiece(new Position(x, y));
				if ((p != null) && (p.getSide() == attacker) && p.getMoves(false).containsDest(kingPos)) {

					return true;
				}
			}
		}
		return false;
	}
}
