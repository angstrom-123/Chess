package com.ang.Util;

import com.ang.Pieces.Piece;
import com.ang.Pieces.PieceColour;
import com.ang.Pieces.PieceType;

public class BoardRecord {
    public Piece[] board;
    public int whiteKingPos;
    public int blackKingPos;
    public int epPawnPos;

    public void set(BoardRecord rec) {
        this.board = rec.board;
        this.whiteKingPos = rec.whiteKingPos;
        this.blackKingPos = rec.blackKingPos;
        this.epPawnPos = rec.epPawnPos;
    }

    public void move(int from, int to, boolean enPassant) {
        board[to] = board[from];
        board[from] = new Piece();
        
        if (enPassant) { 
            board[epPawnPos] = null;
            epPawnPos = -1;
        }
    }

    public PieceType pieceAt(int pos) {
        if (board[pos] == new Piece()) {
            return PieceType.NONE;
        }
        return board[pos].type();
    }

    public PieceColour colourAt(int pos) {
        if (board[pos] == new Piece()) {
            return PieceColour.NONE;
        }
        return board[pos].colour();
    }
}
