package com.ang.Util;

import com.ang.Pieces.Piece;
import com.ang.Pieces.PieceColour;
import com.ang.Pieces.PieceType;

public class BoardRecord {
    public Piece[] board;
    public int whiteKingPos;
    public int blackKingPos;
    public int epPawnPos;

    public BoardRecord(Piece[] board, int white, int black, int epPawn) {
        this.board = board;
        this.whiteKingPos = white;
        this.blackKingPos = black;
        this.epPawnPos = epPawn;
    }

    public void set(BoardRecord rec) {
        this.board = rec.board;
        this.whiteKingPos = rec.whiteKingPos;
        this.blackKingPos = rec.blackKingPos;
        this.epPawnPos = rec.epPawnPos;
    }

    public MoveList possibleMoves(int index) {
        return board[index].getMoves(this);
    }

    public MoveList possibleMoves(PieceColour col) {
        MoveList moves = new MoveList(16 * 27);
        for (int i = 0; i < board.length; i++) {
            if (colourAt(i) != col) {
                continue;
            }

            moves.add(board[i].getMoves(this));
        }
        return moves;
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
