package com.nullprogram.chess.gui;

import com.nullprogram.chess.Board;
import com.nullprogram.chess.Game;

public class FrameController {

	private ChessFrame frame;
	
    private Board board;

    /** The current game. */
    private Game game; 
    
    //TO DO : instancier et modifier accès
    BoardController boardController;
    
	public FrameController(Board board) {
		this.frame = null;
		this.board = board;
		this.game = null;
		
	}


	public void attach(ChessFrame frame) {
		this.frame = frame;
		this.boardController = new BoardController(board, frame.getBoardView());
	}
	
	public void setBoard(Board board){	
		this.board = board; 
		boardController.setBoard(board);
		frame.getBoardView().invalidate();
	}
	
    /**
     * Set up a new game.
     */
    public final void newGame() {
        NewGame ngFrame = new NewGame(frame);
        ngFrame.setVisible(true);
        Game newGame = ngFrame.getGame();
        if (newGame == null) {
            return;
        }
        if (game != null) {
            game.end();
        }
        
        game = newGame;
        
        setBoard(newGame.getBoard());      
        //Mettre à jour les controllers
        frame.setSize(frame.getPreferredSize());
        
        
        frame.getProgressBarView().setGame(game);
        game.addGameListener(frame);
        game.addGameListener(boardController);
        game.begin();
    }

}
