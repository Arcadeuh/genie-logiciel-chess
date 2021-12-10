package com.nullprogram.chess.view;

import com.nullprogram.chess.models.Board;
import com.nullprogram.chess.models.Move;
import com.nullprogram.chess.models.MoveList;
import com.nullprogram.chess.models.Piece;
import com.nullprogram.chess.models.Position;
import com.nullprogram.chess.controllers.BoardController;
import com.nullprogram.chess.controllers.MouseHandler;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 * Displays a board and exposes local players.
 *
 * This swing element displays a game board and can also behave as a
 * player as needed.
 */
public class BoardView extends JComponent {

    /** This class's Logger. */
    private static final Logger LOG =
        Logger.getLogger("com.nullprogram.chess.view.BoardView");

    /** Size of a tile in working coordinates. */
    static final double TILE_SIZE = 200.0;

    /** Shape provided for drawing background tiles. */
    private static final Shape TILE =
        new Rectangle2D.Double(0, 0, TILE_SIZE, TILE_SIZE);

    /** Padding between the highlight and tile border. */
    static final int HIGHLIGHT_PADDING = 15;

    /** Thickness of highlighting. */
    static final Stroke HIGHLIGHT_STROKE = new BasicStroke(12);

    /** Shape for drawing the highlights. */
    private static final Shape HIGHLIGHT =
        new RoundRectangle2D.Double(HIGHLIGHT_PADDING, HIGHLIGHT_PADDING,
                                    TILE_SIZE - HIGHLIGHT_PADDING * 2,
                                    TILE_SIZE - HIGHLIGHT_PADDING * 2,
                                    HIGHLIGHT_PADDING * 4,
                                    HIGHLIGHT_PADDING * 4);

    /** Version for object serialization. */
    private static final long serialVersionUID = 1L;

    /** The Controller of the board being displayed. */
    private BoardController boardController;

    /** The Controller of the board being displayed. */
    private MouseHandler mouseHandler;

    /** Indicate flipped status. */
    private boolean flipped = true;

    /** The color for the dark tiles on the board. */
    static final Color DARK = new Color(0xD1, 0x8B, 0x47);

    /** The color for the light tiles on the board. */
    static final Color LIGHT = new Color(0xFF, 0xCE, 0x9E);

    /** Border color for a controller.getSelected(). tile. */
    static final Color SELECTED = new Color(0x00, 0xFF, 0xFF);

    /** Border color for a highlighted movement tile. */
    static final Color MOVEMENT = new Color(0x7F, 0x00, 0x00);

    /** Last move highlight color. */
    static final Color LAST = new Color(0x00, 0x7F, 0xFF);

    /** Minimum size of a tile, in pixels. */
    static final int MIN_SIZE = 25;

    /** Preferred size of a tile, in pixels. */
    static final int PREF_SIZE = 75;

    /**
     * Hidden constructor.
     */
    protected BoardView() {
    }

    /**
     * Create a new display for given board.
     *
     * @param displayBoard the board to be displayed
     */
    public BoardView(Board board) {
        this.boardController = new BoardController(board,this);
        this.mouseHandler = new MouseHandler(boardController);
        addMouseListener(mouseHandler);
    }

    /**
     * Set the preferred board size.
     * 
     * @param width new width size
     * @param height new height size
     */
    public void updateSize(int width, int height) {
        setPreferredSize(new Dimension(PREF_SIZE * width,
                                       PREF_SIZE * height));
        setMinimumSize(new Dimension(MIN_SIZE * width,
                                     MIN_SIZE * height));
    }

    @Override
    public final Dimension getPreferredSize() {
        return new Dimension(PREF_SIZE * boardController.getBoard().getWidth(),
                             PREF_SIZE * boardController.getBoard().getHeight());
    }

    /**
     * Determine which tile a pixel point belongs to.
     *
     * @param p the point
     * @return  the position on the board
     */
    public Position getPixelPosition(final Point2D p) {
        Point2D pout = null;
        try {
            pout = getTransform().inverseTransform(p, null);
        } catch (java.awt.geom.NoninvertibleTransformException t) {
            /* This will never happen. */
            return null;
        }
        int x = (int) (pout.getX() / BoardView.TILE_SIZE);
        int y = (int) (pout.getY() / BoardView.TILE_SIZE);
        if (getFlipped()) {
            y = boardController.getBoard().getHeight() - 1 - y;
        }
        return new Position(x, y);
    }
    
    /**
     * Get Flipped status.
     * 
     * @return boolean flip status
     */
    public boolean getFlipped() {
    	
    	return flipped;
    }
    
    /**
     * Get BoardController
     * 
     * @return BoardController
     */ 
    public BoardController getBoardController() {
    	
    	return boardController;
    }
    
    /**
     * Return the transform between working space and drawing space.
     *
     * @return display transform
     */
    public final AffineTransform getTransform() {
        AffineTransform at = new AffineTransform();
        at.scale(getWidth() / (TILE_SIZE * boardController.getBoard().getWidth()),
                 getHeight() / (TILE_SIZE * boardController.getBoard().getHeight()));
        return at;
    }

    /**
     * Standard painting method.
     *
     * @param graphics the drawing surface
     */
    public final void paintComponent(final Graphics graphics) {
        
    	Graphics2D g = (Graphics2D) graphics;
        Board board = boardController.getBoard();
        int h = board.getHeight();
        int w = board.getWidth();
        g.transform(getTransform());
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                           RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
                           RenderingHints.VALUE_STROKE_PURE);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                           RenderingHints.VALUE_RENDER_QUALITY);

        /* Temp AffineTransform for the method */
        AffineTransform at = new AffineTransform();

        /* Draw the background */
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                if ((x + y) % 2 == 0) {
                    g.setColor(LIGHT);
                } else {
                    g.setColor(DARK);
                }
                at.setToTranslation(x * TILE_SIZE, y * TILE_SIZE);
                g.fill(at.createTransformedShape(TILE));
            }
        }

        /* Place the pieces */
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                Piece p = board.getPiece(new Position(x, y));
                if (p != null) {
                    Image tile = p.getImage();
                    int yy = y;
                    if (flipped) {
                        yy = board.getHeight() - 1 - y;
                    }
                    at.setToTranslation(x * TILE_SIZE, yy * TILE_SIZE);
                    g.drawImage(tile, at, null);
                }
            }
        }

        /* Draw last move */
        Move last = board.last();
        if (last != null) {
            g.setColor(LAST);
            highlight(g, last.getOrigin());
            highlight(g, last.getDest());
        }

        /* Draw selected square */
        if (mouseHandler.getSelected() != null) {
            g.setColor(SELECTED);
            highlight(g, mouseHandler.getSelected());

            /* Draw piece moves */
            MoveList moves = boardController.getMoves();
            if (moves != null) {
                g.setColor(MOVEMENT);
                for (Move move : moves) {
                    highlight(g, move.getDest());
                }
            }
        }
    }

    /**
     * Highlight the given tile on the board using the current color.
     *
     * @param g   the drawing surface
     * @param pos position to highlight
     */
    private void highlight(final Graphics2D g, final Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        if (flipped) {
            y = boardController.getBoard().getHeight() - 1 - y;
        }
        g.setStroke(HIGHLIGHT_STROKE);
        AffineTransform at = new AffineTransform();
        at.translate(x * TILE_SIZE, y * TILE_SIZE);
        g.draw(at.createTransformedShape(HIGHLIGHT));
    }

    /**
     * Return the desired aspect ratio of the board.
     *
     * @return desired aspect ratio
     */
    public final double getRatio() {
        return boardController.getBoard().getWidth() / (1.0 * boardController.getBoard().getHeight());
    }

}
