package com.nullprogram.chess.models.pieces;

import com.nullprogram.chess.models.MoveList;
import com.nullprogram.chess.models.Piece;
import com.nullprogram.chess.models.Side;

import java.io.Serial;

/**
 * The Chess archbishop.
 *
 * This class describes the movement and capture behavior of the Chess
 * archbishop (bishop + knight).
 */
public class Archbishop extends Piece {

	/** Serialization identifier. */
	@Serial
	private static final long serialVersionUID = -172677440L;

	/**
	 * Create a new archbishop on the given side.
	 *
	 * @param side piece owner
	 */
	public Archbishop(final Side side) {
		super(side, "Archbishop");
	}

	@Override
	public final MoveList getMoves(final boolean check) {
		MoveList list = new MoveList(getBoard(), check);
		/* Take advantage of the Bishop and Knight implementations */
		list = Bishop.getMoves(this, list);
		list = Knight.getMoves(this, list);
		return list;
	}
}
