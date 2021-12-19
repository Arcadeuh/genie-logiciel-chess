package com.nullprogram.chess.controllers;

import com.nullprogram.chess.models.Board;
import com.nullprogram.chess.models.Side;
import com.nullprogram.chess.models.GameEvent;
import com.nullprogram.chess.models.GameListener;
import com.nullprogram.chess.models.Move;
import com.nullprogram.chess.models.MoveList;
import com.nullprogram.chess.models.Player;
import com.nullprogram.chess.view.BoardView;

import java.io.Serial;
import java.io.Serializable;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

/**
 * Displays a board and exposes local players.
 *
 * This swing element displays a game board and can also behave as a player as
 * needed.
 */
public class BoardController implements Player, GameListener, Serializable {

	/** This class's Logger. */
	private static final Logger LOG = Logger.getLogger("com.nullprogram.chess.controllers.BoardController");

	/** Version for object serialization. */
	@Serial
	private static final long serialVersionUID = 145678456L;

	/** The board being displayed. */
	private Board boardModel;

	/** The boardView being displayed. */
	private final BoardView boardView;

	/** The list of moves for the selected tile. */
	private MoveList moves = null;

	/** The current interaction mode. */
	private Mode mode = Mode.WAIT;

	/** Current player making a move, when interactive. */
	private Side side;

	/** Latch to hold down the Game thread while the user makes a selection. */
	private transient CountDownLatch latch;

	/** The move selected by the player. */
	private Move selectedMove;


	/**
	 * Create a new display for given board.
	 *
	 * @param board     the board to be displayed
	 * @param boardView corresponding view
	 */
	public BoardController(final Board board, BoardView boardView) {
		boardModel = board;
		this.boardView = boardView;
	}

	/**
	 * Change the board to be displayed.
	 *
	 * @param b the new board
	 */
	public final void setBoard(final Board b) {
		boardModel = b;
		boardView.updateSize(b.getHeight(), b.getWidth());
		boardView.repaint();
	}

	/**
	 * Change the board to be displayed.
	 *
	 * @return display's board
	 */
	public final Board getBoard() {
		return boardModel;
	}

	/**
	 * Change the board to be displayed.
	 *
	 * @return display's board
	 */
	public final BoardView getBoardView() {
		return boardView;
	}

	/**
	 * Get move selected by user
	 *
	 * @return MoveList
	 */
	public MoveList getMoves() {
		return moves;
	}

	/**
	 * Set move selected by user
	 */
	public void setMoves(MoveList newMoves) {
		this.moves = newMoves;
	}

	@Override
	public final void gameEvent(final GameEvent e) {
		setBoard(e.getGame().getBoard());
		if (e.getType() != GameEvent.STATUS) {
			boardView.repaint();
		}
	}

	@Override
	public final Move takeTurn(final Board turnBoard, final Side currentSide) {
		latch = new CountDownLatch(1);
		setBoard(turnBoard);
		side = currentSide;
		boardView.repaint();
		mode = Mode.PLAYER;
		try {
			latch.await();
		} catch (InterruptedException e) {
			LOG.warning("BoardController interrupted during turn.");
			Thread.currentThread().interrupt();
		}
		return selectedMove;
	}


	/**
	 * Get thread latch
	 *
	 * @return CountDownLatch
	 */
	public CountDownLatch getLatch() {
		return this.latch;
	}

	/**
	 * Get mode of UI with player, interacting or ignoring him
	 *
	 * @return Mode
	 */
	public Mode getMode() {
		return this.mode;
	}

	/**
	 * Get side of current player
	 *
	 * @return Side
	 */
	public Side getSide() {
		return this.side;
	}

	/**
	 * Setter on selected Move (determined by MouseHandler)
	 *
	 */
	public void setSelectedMove(Move move) {
		this.selectedMove = move;
	}

	/**
	 * Setter on side (determined by MouseHandler)
	 *
	 */
	public void setMode(Mode mode) {
		this.mode = mode;
	}
}
