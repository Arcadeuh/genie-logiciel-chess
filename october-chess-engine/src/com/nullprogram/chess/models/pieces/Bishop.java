package com.nullprogram.chess.models.pieces;

import com.nullprogram.chess.models.Move;
import com.nullprogram.chess.models.MoveList;
import com.nullprogram.chess.models.Piece;
import com.nullprogram.chess.models.Position;
import com.nullprogram.chess.models.Side;

import java.io.Serial;

/**
 * The Chess bishop.
 *
 * This class describes the movement and capture behavior of the Chess bishop.
 */
public class Bishop extends Piece {

	/** Serialization identifier. */
	@Serial
    private static final long serialVersionUID = 292046969L;

	/**
	 * Create bishop with given side.
	 *
	 * @param side piece side
	 */
	public Bishop(final Side side) {
		super(side, "Bishop");
	}

	@Override
	public final MoveList getMoves(final boolean check) {
		MoveList list = new MoveList(getBoard(), check);
		list = getMoves(this, list);
		return list;
	}

    /**
     * Determine bishop moves for given situation.
     *
     * This method is here for the purposes of reuse.
     *
     * @param p     the piece being tested
     * @param list  list to be appended to
     * @return      the modified list
     */
    public static final MoveList getMoves(final Piece p,
                                          final MoveList list) {
        /* Scan each direction and stop looking when we run into something. */
        Position home = p.getPosition();
        int x = home.getX();
        int y = home.getY();
        while (x >= 0 && y >= 0) {
            x--;
            y--;
            Position pos = new Position(x, y);
            if(!list.addPieceMove(home,pos)) {
        		break;
        	}
        }
        x = home.getX();
        y = home.getY();
        while (x < p.getBoard().getWidth() &&
               y < p.getBoard().getHeight()) {

            x++;
            y++;
            Position pos = new Position(x, y);
            if(!list.addPieceMove(home,pos)) {
        		break;
        	}
        }
        x = home.getX();
        y = home.getY();
        while (x >= 0 && y < p.getBoard().getHeight()) {
            x--;
            y++;
            Position pos = new Position(x, y);
            if(!list.addPieceMove(home,pos)) {
        		break;
        	}
        }
        x = home.getX();
        y = home.getY();
        while (x < p.getBoard().getWidth() && y >= 0) {
            x++;
            y--;
            Position pos = new Position(x, y);
            if(!list.addPieceMove(home,pos)) {
        		break;
        	}
        }
        return list;
    }
}
