package com.nullprogram.chess.models.boards;

import com.nullprogram.chess.models.Board;

/**
 * Creates a new board on demand, for use of board copying.
 */
public final class BoardFactory {

	/**
	 * The Gothic chess board.
	 */
	private static final Class<?> gothic = Gothic.class;

	/**
	 * The standard chess board.
	 */
	private static final Class<?> standard = StandardBoard.class;

	/**
	 * An empty chess board.
	 */
	private static final Class<?> empty = EmptyBoard.class;

	/**
	 * Hidden constructor.
	 */
	private BoardFactory() {
	}

	/**
	 * Create a new chess board of the given class.
	 *
	 * @param board class to be created
	 * @return a fresh board
	 */
	public static Board create(final Class<?> board) {
		if (board.equals(standard)) {
			return new StandardBoard();
		} else if (board.equals(gothic)) {
			return new Gothic();
		} else if (board.equals(empty)) {
			return new EmptyBoard();
		} else {
			/* Throw exception? */
			return null;
		}
	}
}
