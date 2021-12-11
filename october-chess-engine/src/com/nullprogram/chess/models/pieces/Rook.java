package com.nullprogram.chess.models.pieces;

import com.nullprogram.chess.models.MoveList;
import com.nullprogram.chess.models.Piece;
import com.nullprogram.chess.models.Position;
import com.nullprogram.chess.models.Side;

import java.io.Serial;

/**
 * The Chess rook.
 *
 * This class describes the movement and capture behavior of the Chess rook.
 */
public class Rook extends Piece {

	/** Serialization identifier. */
	@Serial
    private static final long serialVersionUID = 239867335L;

	/**
	 * Create a new rook on the given side.
	 *
	 * @param side piece owner
	 */
	public Rook(final Side side) {
		super(side, "Rook");
	}

	@Override
	public final MoveList getMoves(final boolean check) {
		MoveList list = new MoveList(getBoard(), check);
		list = getMoves(this, list);
		return list;
	}

	/**
	 * Determine rook moves for given situation.
	 *
	 * This method is here for the purposes of reuse.
	 *
	 * @param p    the piece being tested
	 * @param list list to be appended to
	 * @return the modified list
	 */
	public static MoveList getMoves(final Piece p, final MoveList list) {
		/* Scan each direction and stop looking when we run into something. */
		Position home = p.getPosition();
		int x = home.getX();
		int y = home.getY();
		while (x >= 0) {
			x--;
			Position pos = new Position(x, y);
			if (list.addPieceMove(home, pos)) {
				break;
			}
		}
		x = home.getX();
		y = home.getY();
		while (x < p.getBoard().getWidth()) {
			x++;
			Position pos = new Position(x, y);
			if (list.addPieceMove(home, pos)) {
				break;
			}
		}
		x = home.getX();
		y = home.getY();
		while (y >= 0) {
			y--;
			Position pos = new Position(x, y);
			if (list.addPieceMove(home, pos)) {
				break;
			}
		}
		x = home.getX();
		y = home.getY();
		while (y < p.getBoard().getHeight()) {
			y++;
			Position pos = new Position(x, y);
			if (list.addPieceMove(home, pos)) {
				break;
			}
		}
		return list;
	}
}
