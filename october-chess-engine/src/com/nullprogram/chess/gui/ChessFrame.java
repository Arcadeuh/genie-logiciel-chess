package com.nullprogram.chess.gui;

import com.nullprogram.chess.Board;
import com.nullprogram.chess.Chess;
import com.nullprogram.chess.Game;
import com.nullprogram.chess.GameEvent;
import com.nullprogram.chess.GameListener;
import com.nullprogram.chess.MenuHandler;
import com.nullprogram.chess.Player;
import com.nullprogram.chess.boards.EmptyBoard;
import com.nullprogram.chess.pieces.ImageServer;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

/**
 * The JFrame that contains all GUI elements.
 */
public class ChessFrame extends JFrame
    implements ComponentListener, GameListener {

    /** Version for object serialization. */
    private static final long serialVersionUID = 1L;

    /** The board display. */
    private BoardView display;
    
    /** The progress bar on the display. */
    private final StatusBar progress;
    
    //TO DO
    private FrameController controller;

    /**
     * Create a new ChessFrame for the given board.
     */
    public ChessFrame(Board board, FrameController controller) {
        super("V1.0");
        setResizable(true);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(ImageServer.getTile("King-WHITE"));
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.controller = controller;
        
        //Attacher le menu
        MenuHandler handler = new MenuHandler(this, controller);
        handler.setUpMenu();

        //Attacher la vue du plateau
        //TO DO 
        display = new BoardView(board);
        
        //Attacher la vue de la barre de progression
        progress = new StatusBar(null);
        add(display);
        add(progress);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }



    /**
     * Return the GUI (human) play handler.
     *
     * @return the player
     */
    public final Player getPlayer() {
    	
    	//TO DO
        return controller.boardController;
    }

    public BoardView getBoardView() {
    	return this.display;
    }
    
    public StatusBar getProgressBarView() {
    	return this.progress;
    }

    @Override
    public final void componentResized(final ComponentEvent e) {
        if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) != 0) {
            /* If the frame is maxmized, the battle has been lost. */
            return;
        }
        double ratio = display.getRatio();
        double barh = progress.getPreferredSize().getHeight();
        Container p = getContentPane();
        Dimension d = null;
        if (p.getWidth() * ratio < (p.getHeight() - barh)) {
            d = new Dimension((int) ((p.getHeight() - barh) * ratio),
                              p.getHeight());
        } else if (p.getWidth() * ratio > (p.getHeight() - barh)) {
            d = new Dimension(p.getWidth(),
                              (int) (p.getWidth() / ratio + barh));
        }
        if (d != null) {
            p.setPreferredSize(d);
            pack();
        }
    }

    //TO DO : retirer 
    @Override
    public final void gameEvent(final GameEvent e) {
        progress.repaint();
    }

    @Override
    public void componentHidden(final ComponentEvent e) {
        /* Do nothing. */
    }

    @Override
    public void componentMoved(final ComponentEvent e) {
        /* Do nothing. */
    }

    @Override
    public void componentShown(final ComponentEvent e) {
        /* Do nothing. */
    }
}
