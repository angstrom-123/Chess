package com.ang;

import com.ang.Graphics.Renderer;
import com.ang.Pieces.*;
import com.ang.Util.BoardRecord;
import com.ang.Util.FENReader;

public class Game implements GameInterface {
    final static int SQUARE_SIZE = 45;
    final static double RENDER_SCALE = 1.2;

    private int selectedSquare = -1;

    private BoardRecord currentBoard;
    private PieceColour colToMove = PieceColour.WHITE;
    private Renderer renderer;

    public void start() {
        currentBoard = new BoardRecord();

        Piece[] b = FENReader.readFEN("rnbqkbnrpppppppp8888PPPPPPPPRNBQKBNR");
        currentBoard.board = b;
        currentBoard.whiteKingPos = 60;
        currentBoard.blackKingPos = 4;
        currentBoard.epPawnPos = -1;

        renderer = new Renderer(SQUARE_SIZE, RENDER_SCALE, this);
        renderer.drawAllSprites(currentBoard);
    }

    public void mouseClick(int x, int y) {
        double actualSquareSize = Math.round(SQUARE_SIZE * RENDER_SCALE);
        int xCoord = (int)Math.floor((double)x / actualSquareSize);
        int yCoord = (int)Math.floor((double)y / actualSquareSize);

        squarePressed(xCoord, yCoord);
    }

    public void squarePressed(int x, int y) {
        renderer.drawBoard(); 

        int pressedSquare = y * 8 + x;   
        if (currentBoard.colourAt(pressedSquare) == colToMove) {
            renderer.highlightSquare(x, y);
            selectedSquare = pressedSquare;
        } else if ((selectedSquare > -1) 
                && (currentBoard.colourAt(pressedSquare) != colToMove)) {
            boolean moved = currentBoard.tryMove(selectedSquare, pressedSquare);
            if (moved) {
                colToMove = (colToMove == PieceColour.WHITE) 
                ? PieceColour.BLACK 
                : PieceColour.WHITE;
                selectedSquare = -1;
            }
        }

        renderer.drawAllSprites(currentBoard);  
    }
}
