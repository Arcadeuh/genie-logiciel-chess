package com.nullprogram.chess.controllers;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

import com.nullprogram.chess.models.Move;
import com.nullprogram.chess.models.MoveList;
import com.nullprogram.chess.models.Piece;
import com.nullprogram.chess.models.Position;

public class MouseHandler implements MouseListener, Serializable {

	BoardController boardController;

	/** Version for object serialization. */
	private static final long serialVersionUID = 145978456L;

	/** The currently selected tile. */
	private Position selected = null;

	public MouseHandler(BoardController bc) {
		this.boardController = bc;
	}

	@Override
	public final void mouseReleased(final MouseEvent e) {
		
		if (e.getButton() == MouseEvent.BUTTON1 ){
			leftClick(e);
		}
		
		boardController.getBoardView().repaint();
	}

	/**
	 * Handle the event when the left button is clicked.
	 *
	 * @param e the mouse event
	 */
	private void leftClick(final MouseEvent e) {
		if (boardController.getMode() == Mode.WAIT) {
			return;
		}

		MoveList moves = boardController.getMoves();

		Position pos = boardController.getBoardView().getPixelPosition(e.getPoint()); // Return selected case
																						// (represented by a position)

		Boolean outsideClick = boardController.getBoard().inRange(pos);
		if (Boolean.FALSE.equals(outsideClick)) {
			/* Click was outside the board, somehow. */
			return;
		}
		if (pos != null) {
			if (pos.equals(selected)) {// If case is re-clicked
				/* Deselect */
				selected = null;
				boardController.setMoves(null);
			} else if (moves != null && moves.containsDest(pos)) {// Change place of a piece if player select a move
																	// possible for this piece
				/* Move selected piece */
				boardController.setMode(Mode.WAIT);
				Move move = moves.getMoveByDest(pos);
				selected = null;
				boardController.setMoves(null);
				boardController.setSelectedMove(move);
				boardController.getLatch().countDown();
			} else {
				/* Select this position */
				Piece p = boardController.getBoard().getPiece(pos);
				if (p != null && p.getSide() == boardController.getSide()) {
					selected = pos;
					boardController.setMoves(p.getMoves(true));
				}
			}
		}
	}

	/**
	 * Get Position selected by user
	 *
	 * @return Position
	 */
	public Position getSelected() {
		return selected;
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		// Do nothing
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		// Do nothing
	}
}
