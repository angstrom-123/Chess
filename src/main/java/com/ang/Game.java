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

    private BoardRecord realRec;
    private PieceColour colToMove = PieceColour.WHITE;
    private Renderer renderer;
    private Engine engine;

    public Game(Engine engine) {
        this.engine = engine;
    }

    public void start() {
        realRec = new BoardRecord();

        Piece[] b = FENReader.readFEN("rnbqkbnrpppppppp8888PPPPPPPPRNBQKBNR");
        realRec.board = b;
        realRec.whiteKingPos = 60;
        realRec.blackKingPos = 4;
        realRec.epPawnPos = -1;

        renderer = new Renderer(SQUARE_SIZE, RENDER_SCALE, this);
        renderer.drawAllSprites(realRec);
    }

    public void mouseClick(int x, int y) {
        double actualSquareSize = Math.round(SQUARE_SIZE * RENDER_SCALE);
        int xCoord = (int)Math.floor((double)x / actualSquareSize);
        int yCoord = (int)Math.floor((double)y / actualSquareSize);

        squarePressed(xCoord, yCoord);
    }

    public void squarePressed(int x, int y) {
        int pressed = y * 8 + x;   
        if (realRec.colourAt(pressed) == colToMove) {
            renderer.highlightSquare(x, y);
            selected = pressed;
            refreshBoard(realRec);  
            showMoves(selected);
        } else if ((selected > -1) && (realRec.colourAt(pressed) != colToMove)) {
            Move moveToMake = new Move(realRec.board[selected], 
                    selected, pressed);
            
            boolean moved = realRec.tryMove(moveToMake);

            if (moved) {
                refreshBoard(realRec);
                Move engineMove = engine.generateMove(realRec);
                realRec.tryMove(engineMove);
                selected = -1;
                refreshBoard(realRec);
            }
        }     
    }

    public void refreshBoard(BoardRecord rec) {
        renderer.drawBoard();
        renderer.drawAllSprites(rec);
    }

    // TODO : only show possible moves
    public void showMoves(int pos) {
        MoveList moves = realRec.board[selected].getMoves(realRec);
        for (int i = 0; i < moves.length() - 1; i++) {
            int markX = moves.at(i).to() % 8;
            int markY = (int)Math.floor(moves.at(i).to() / 8);
            renderer.drawMarker(markX, markY);
        }
    }
}
