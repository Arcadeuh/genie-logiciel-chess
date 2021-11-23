package com.nullprogram.chess;

import com.nullprogram.chess.ai.Minimax;
import com.nullprogram.chess.boards.StandardBoard;
import com.nullprogram.chess.gui.BoardController;
import com.nullprogram.chess.gui.BoardView;

import java.util.logging.Logger;
import javax.swing.JApplet;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Applet that runs a game versus the computer, no other options.
 */
public final class ChessApplet extends JApplet implements GameListener {

    /** This class's Logger. */
    private static final Logger LOG =
        Logger.getLogger("com.nullprogram.chess.ChessApplet");

    /** Version for object serialization. */
    private static final long serialVersionUID = 34863129470926196L;

    @Override
    public void init() {
        try {
            String lnf = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(lnf);
        } catch (IllegalAccessException e) {
            LOG.warning("Failed to access 'Look and Feel'");
        } catch (InstantiationException e) {
            LOG.warning("Failed to instantiate 'Look and Feel'");
        } catch (ClassNotFoundException e) {
            LOG.warning("Failed to find 'Look and Feel'");
        } catch (UnsupportedLookAndFeelException e) {
            LOG.warning("Failed to set 'Look and Feel'");
        }

        StandardBoard board = new StandardBoard();
        BoardView panel = new BoardView(board);
        add(panel);
        Game game = new Game(board);
        BoardController boardController = panel.getBoardController();
        game.seat(boardController, new Minimax(game));
        game.addGameListener(this);
        game.addGameListener(boardController);
        game.begin();
    }

    @Override
    public void gameEvent(final GameEvent e) {
        if (e.getGame().isDone()) {
            String message;
            Piece.Side winner = e.getGame().getWinner();
            if (winner == Piece.Side.WHITE) {
                message = "White wins";
            } else if (winner == Piece.Side.BLACK) {
                message = "Black wins";
            } else {
                message = "Stalemate";
            }
            JOptionPane.showMessageDialog(null, message);
        }
    }
}
