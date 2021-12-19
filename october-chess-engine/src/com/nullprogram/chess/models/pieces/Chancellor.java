package com.nullprogram.chess.models.pieces;

import com.nullprogram.chess.models.MoveList;
import com.nullprogram.chess.models.Piece;
import com.nullprogram.chess.models.Side;

import java.io.Serial;

/**
 * The Chess chancellor.
 *
 * This class describes the movement and capture behavior of the Chess
 * chancellor (rook + knight).
 */
public class Chancellor extends Piece {

	/** Serialization identifier. */
	@Serial
    private static final long serialVersionUID = -421088543L;

	/**
	 * Create a new chancellor on the given side.
	 *
	 * @param side piece owner
	 */
	public Chancellor(final Side side) {
		super(side, "Chancellor");
	}

	@Override
	public final MoveList getMoves(final boolean check) {
		MoveList list = new MoveList(getBoard(), check);
		/* Take advantage of the Rook and Knight implementations. */
		list = Rook.getMoves(this, list);
		list = Knight.getMoves(this, list);
		return list;
	}
}
