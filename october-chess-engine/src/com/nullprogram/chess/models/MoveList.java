package com.nullprogram.chess.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Safe list of moves.
 *
 * Before a move is added it can be checked for some basic validity.
 */
public class MoveList implements Iterable<Move>, Serializable {

	/** Versioning for object serialization. */
	@Serial
	private static final long serialVersionUID = -25601206293390593L;

	/** The board used to verify positions before adding them. */
	private final Board board;

	/** Should we check for check when verifying moves. */
	private final boolean check;

	/** The actual list of moves. */
	private final List<Move> moves = new ArrayList<>();

	/**
	 * Create a new move list relative to a board.
	 *
	 * @param verifyBoard the board to be used
	 */
	public MoveList(final Board verifyBoard) {
		this(verifyBoard, true);
	}

	/**
	 * Create a new move list relative to a board.
	 *
	 * @param checkCheck  check for check
	 * @param verifyBoard the board to be used
	 */
	public MoveList(final Board verifyBoard, final boolean checkCheck) {
		board = verifyBoard;
		check = checkCheck;
	}

	/**
	 * Add a move without verifying it.
	 * 
	 * @param move move to be added
	 */
	public final void add(final Move move) {
		moves.add(move);
	}

	/**
	 * Add a collection of moves to this one.
	 * 
	 * @param list a collection of moves
	 */
	public final void addAll(final Iterable<Move> list) {
		for (Move move : list) {
			moves.add(move);
		}
	}

	/**
	 * Add move to list if piece can legally move there (no capture).
	 *
	 * @param move move to be added
	 * @return true if position was added
	 */
	public final boolean addMove(final Move move) {
		if (Boolean.TRUE.equals(board.isFree(move.getDest()))) {
			if (causesCheck(move)) {
				add(move);
				return true;
			}
			return true; // false only for a "blocking" move
		}
		return false;
	}

	/**
	 * Add move to list if piece can move <i>or</i> capture at destination.
	 *
	 * @param move position to be added
	 * @return true if position was added
	 */
	public final boolean addCapture(final Move move) {
		Piece p = board.getPiece(move.getOrigin());
		if (Boolean.TRUE.equals(board.isFree(move.getDest(), p.getSide()))) {
			if (causesCheck(move)) {
				add(move);
				return true;
			}
			return true; // false only for a "blocking" move
		}
		return false;
	}

	/**
	 * Add move to list only if the piece will perform a capture.
	 *
	 * @param move position to be added
	 */
	public final void addCaptureOnly(final Move move) {
		Piece p = board.getPiece(move.getOrigin());
		if (Boolean.TRUE.equals(board.isFree(move.getDest(), p.getSide()) && !board.isFree(move.getDest())) && causesCheck(move)) {

			add(move);
		}
	}

	/**
	 * Determine if move will cause check for the same side.
	 *
	 * @param move move to be tested
	 * @return true if move causes check
	 */
	private boolean causesCheck(final Move move) {
		if (!check) {
			return true;
		}
		Piece p = board.getPiece(move.getOrigin());
		board.move(move);
		boolean ret = board.check(p.getSide());
		board.undo();
		return !ret;
	}

	/**
	 * Return true if this list contains the position as a destination.
	 *
	 * @param pos destination position
	 * @return true if destination is present in list
	 */
	public final boolean containsDest(final Position pos) {
		for (Move move : this) {
			if (pos.equals(move.getDest())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the move containing the destination.
	 *
	 * @param dest destination position
	 * @return move containing given destination
	 */
	public final Move getMoveByDest(final Position dest) {
		for (Move move : this) {
			if (dest.equals(move.getDest())) {
				return move;
			}
		}
		return null;
	}

	/**
	 * Stack behavior: pop off the last element on return it.
	 *
	 * @return move popped off the stack
	 */
	public final Move pop() {
		if (isEmpty()) {
			return null;
		}
		Move last = moves.get(moves.size() - 1);
		moves.remove(moves.size() - 1);
		return last;
	}

	/**
	 * Stack behavior: peek at last element.
	 *
	 * @return move at the top of the stack
	 */
	public final Move peek() {
		if (isEmpty()) {
			return null;
		}
		return moves.get(moves.size() - 1);
	}

	/**
	 * Get the number of moves in this list.
	 * 
	 * @return the number of moves in this list
	 */
	public final int size() {
		return moves.size();
	}

	/**
	 * Determine if this move list is empty.
	 * 
	 * @return true if empty
	 */
	public final boolean isEmpty() {
		return moves.isEmpty();
	}

	/**
	 * Shuffle the order of the moves in this list.
	 */
	public final void shuffle() {
		Collections.shuffle(moves);
	}

	@Override
	public final Iterator<Move> iterator() {
		return moves.iterator();
	}

	/**
     * Add a move to a listMove of a piece
     * 
     * @param orig is the origin of the move
     * @param dest is the destination of the move
     * @return true if everything have been done
     */
    public final boolean addPieceMove(Position orig, Position dest) {
    	if(!this.addCapture(new Move(orig, dest))) {
    		return false;
    	}
    	if(!board.isFree(dest)) {
    		return false;
    	}
    	return true;
    }
}
