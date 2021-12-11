package com.nullprogram.chess.view;

import com.nullprogram.chess.models.Board;
import com.nullprogram.chess.models.Game;
import com.nullprogram.chess.models.GameEvent;
import com.nullprogram.chess.models.GameListener;
import com.nullprogram.chess.models.Player;
import com.nullprogram.chess.models.boards.EmptyBoard;
import com.nullprogram.chess.controllers.BoardController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.Serial;
import javax.swing.*;

/**
 * The JFrame that contains all GUI elements.
 */
public class ChessFrame extends JFrame implements ComponentListener, GameListener {

	/** Version for object serialization. */
	@Serial
	private static final long serialVersionUID = 1L;

	/** The board display. */
	private final BoardView display;

	/** The board controller. */
	private final transient BoardController panelController;

	/** The progress bar on the display. */
	private final StatusBar progress;

	/** The current game. */
	private transient Game game;

	/**
	 * Create a new ChessFrame for the given board.
	 */
	public ChessFrame() {
		super("V1.0");
		setResizable(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setIconImage(ImageServer.getTile("King-WHITE"));
		setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

		MenuHandler handler = new MenuHandler(this);
		handler.setUpMenu();

		Board emptyBoard = new EmptyBoard();
		display = new BoardView(emptyBoard);
		panelController = display.getBoardController();
		progress = new StatusBar(null);
		add(display);
		add(progress);
		pack();

		addComponentListener(this);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Set up a new game.
	 */
	public final void newGame() {
		NewGame ngFrame = new NewGame(this);
		ngFrame.setVisible(true);
		Game newGame = ngFrame.getGame();
		if (newGame == null) {
			return;
		}
		if (game != null) {
			game.end();
		}
		game = newGame;
		Board board = game.getBoard();
		panelController.setBoard(board);
		display.invalidate();
		setSize(getPreferredSize());

		progress.setGame(game);
		game.addGameListener(this);
		game.addGameListener(panelController);
		game.begin();
	}

	/**
	 * Return the GUI (human) play handler.
	 *
	 * @return the player
	 */
	public final Player getPlayer() {
		return panelController;
	}

	/**
	 * Used for manaing menu events.
	 */
	private class MenuHandler implements ActionListener {

		/** The parent chess frame, for callbacks. */
		private final ChessFrame frame;

		/**
		 * Create the menu handler.
		 *
		 * @param parent parent frame
		 */
		public MenuHandler(final ChessFrame parent) {
			frame = parent;
		}

		@Override
		public final void actionPerformed(final ActionEvent e) {
			if ("New Game".equals(e.getActionCommand())) {
				frame.newGame();
			} else if ("Exit".equals(e.getActionCommand())) {
				System.exit(0);
			}
		}

		/**
		 * Set up the menu bar.
		 */
		public final void setUpMenu() {
			JMenuBar menuBar = new JMenuBar();

            JMenu gameMenu = new JMenu("Game");
			gameMenu.setMnemonic('G');
			JMenuItem newGame = new JMenuItem("New Game");
			newGame.addActionListener(this);
			newGame.setMnemonic('N');
			gameMenu.add(newGame);
			gameMenu.add(new JSeparator());
			JMenuItem exitGame = new JMenuItem("Exit");
			exitGame.addActionListener(this);
			exitGame.setMnemonic('x');
			gameMenu.add(exitGame);
			menuBar.add(gameMenu);

			setJMenuBar(menuBar);
		}
	}

	@Override
	public final void componentResized(final ComponentEvent e) {
		if ((getExtendedState() & Frame.MAXIMIZED_BOTH) != 0) {
			/* If the frame is maxmized, the battle has been lost. */
			return;
		}
		double ratio = display.getRatio();
		double barh = progress.getPreferredSize().getHeight();
		Container p = getContentPane();
		Dimension d = null;
		if (p.getWidth() * ratio < (p.getHeight() - barh)) {
			d = new Dimension((int) ((p.getHeight() - barh) * ratio), p.getHeight());
		} else if (p.getWidth() * ratio > (p.getHeight() - barh)) {
			d = new Dimension(p.getWidth(), (int) (p.getWidth() / ratio + barh));
		}
		if (d != null) {
			p.setPreferredSize(d);
			pack();
		}
	}

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
