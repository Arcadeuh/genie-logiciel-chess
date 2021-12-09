package com.nullprogram.chess.gui;

import com.nullprogram.chess.Board;
import com.nullprogram.chess.Side;
import com.nullprogram.chess.GameEvent;
import com.nullprogram.chess.GameListener;
import com.nullprogram.chess.Move;
import com.nullprogram.chess.MoveList;
import com.nullprogram.chess.Piece;
import com.nullprogram.chess.Player;
import com.nullprogram.chess.Position;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 * Displays a board and exposes local players.
 *
 * This swing element displays a game board and can also behave as a
 * player as needed.
 */
public class BoardController implements Player, GameListener {

    /** This class's Logger. */
    private static final Logger LOG =
        Logger.getLogger("com.nullprogram.chess.gui.BoardController");
    
    /** Version for object serialization. */
    private static final long serialVersionUID = 1L;

    /** The board being displayed. */
    private Board boardModel;

    /** The boardView being displayed. */
    private BoardView boardView;

    /** The list of moves for the selected tile. */
    private MoveList moves = null;

    /** The current interaction mode. */
    private Mode mode = Mode.WAIT;

    /** Current player making a move, when interactive. */
    private Side side;

    /** Latch to hold down the Game thread while the user makes a selection. */
    public CountDownLatch latch;

    /** The move selected by the player. */
    private Move selectedMove;

    /**
     * Hidden constructor.
     */
    protected BoardController() {
    }

    /**
     * Create a new display for given board.
     *
     * @param board the board to be displayed
     * @param boardView corresponding view
     */
    public BoardController(final Board board,BoardView boardView) {
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
        boardView.updateSize(b.getHeight(),b.getWidth());
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
     *
     * @return MoveList
     */
    public void setMoves(MoveList newMoves) {
    	this.moves = newMoves ;
    }

    @Override
    public final void gameEvent(final GameEvent e) {
        setBoard(e.getGame().getBoard());
        if (e.getType() != GameEvent.STATUS) {
        	 boardView.repaint();
        }
    }
    
    
    @Override
    public final Move takeTurn(final Board turnBoard,
                               final Side currentSide) {
        latch = new CountDownLatch(1);
        setBoard(turnBoard);
        side = currentSide;
        boardView.repaint();
        mode = Mode.PLAYER;
        try {
            latch.await();
        } catch (InterruptedException e) {
            LOG.warning("BoardController interrupted during turn.");
        }
        return selectedMove;
    }

    

	public Move getSelectedMove() {
		return selectedMove;
	}
	
	public CountDownLatch getLatch() {
		return this.latch;
	}
	
	public Mode getMode() {
		return this.mode;
	}
	
	public Side getSide() {
		return this.side;
	}
	
	public void setSelectedMove(Move move) {
		this.selectedMove = move;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
}
