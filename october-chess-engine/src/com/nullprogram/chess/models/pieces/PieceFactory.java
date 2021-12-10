package com.nullprogram.chess.models.pieces;

import com.nullprogram.chess.models.Piece;
import com.nullprogram.chess.models.Side;

/**
 * Creates pieces based on their name strings.
 */
public final class PieceFactory {

    /** Hidden constructor. */
    private PieceFactory() {
    }

    /**
     * Create a new piece by name.
     *
     * @param name name of the piece
     * @param side side for the new piece
     * @return the new piece
     */
    public static Piece create(final String name, final Side side) {
        if ("Queen".equals(name)) {
            return new Queen(side);
        } else {
            /* Maybe throw an exception here? */
            return null;
        }
    }
}
