package com.ang;

import com.ang.Graphics.Renderer;
import com.ang.Opponent.Engine;
import com.ang.Pieces.*;
import com.ang.Util.BoardRecord;
import com.ang.Util.FENReader;
import com.ang.Util.Move;
import com.ang.Util.MoveList;

public class Game implements GameInterface {
    final static int SQUARE_SIZE = 45;
    final static double RENDER_SCALE = 1.2;

    private int selected = -1;

    private BoardRecord gameRec;
    private PieceColour colToMove = PieceColour.WHITE;
    private Renderer renderer;
    private Engine engine;

    public Game(Engine engine) {
        this.engine = engine;
    }

    public void start() {
        gameRec = new BoardRecord();

        Piece[] b = FENReader.readFEN("rnbqkbnrpppppppp8888PPPPPPPPRNBQKBNR");
        gameRec.board = b;
        gameRec.whiteKingPos = 60;
        gameRec.blackKingPos = 4;
        gameRec.epPawnPos = -1;
        gameRec.minorPieceCount = 8;
        gameRec.prefetchPiecePositions();

        renderer = new Renderer(SQUARE_SIZE, RENDER_SCALE, this);
        renderer.drawAllSprites(gameRec);
    }

    public void mouseClick(int x, int y) {
        double actualSquareSize = Math.round(SQUARE_SIZE * RENDER_SCALE);
        int xCoord = (int)Math.floor((double)x / actualSquareSize);
        int yCoord = (int)Math.floor((double)y / actualSquareSize);

        squarePressed(xCoord, yCoord);
    }

    public void squarePressed(int x, int y) {
        int pressed = y * 8 + x;   
        if (gameRec.colourAt(pressed) == colToMove) {
            renderer.highlightSquare(x, y);
            selected = pressed;
            refreshBoard(gameRec);  
            showMoves(selected);
        } else if ((selected > -1) && (gameRec.colourAt(pressed) != colToMove)) {
            Move moveToMake = new Move(gameRec.board[selected], 
                    selected, pressed);
            
            boolean moved = gameRec.tryMove(moveToMake);

            if (moved) {
                refreshBoard(gameRec);
                Move engineMove = engine.generateMove(gameRec);
                gameRec.tryMove(engineMove);
                selected = -1;
                refreshBoard(gameRec);
            }
        }     
    }

    public void refreshBoard(BoardRecord rec) {
        renderer.drawBoard();
        renderer.drawAllSprites(rec);
    }

    // TODO : only show possible moves
    public void showMoves(int pos) {
        MoveList moves = gameRec.board[selected].getMoves(gameRec);
        for (int i = 0; i < moves.length() - 1; i++) {
            int markX = moves.at(i).to() % 8;
            int markY = (int)Math.floor(moves.at(i).to() / 8);
            renderer.drawMarker(markX, markY);
        }
    }
}
