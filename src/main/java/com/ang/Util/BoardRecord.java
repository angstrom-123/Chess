package com.ang.Util;

import com.ang.Pieces.Piece;
import com.ang.Pieces.PieceColour;
import com.ang.Pieces.PieceType;

public class BoardRecord {
    public Piece[] board;
    public int whiteKingPos;
    public int blackKingPos;
    public int epPawnPos;

    public BoardRecord() {}

    public BoardRecord copy(BoardRecord rec) {
        BoardRecord temp = new BoardRecord();

        temp.board = new Piece[64];
        for (int i = 0; i < temp.board.length; i++) {
            temp.board[i] = rec.board[i];
        }
        temp.whiteKingPos = rec.whiteKingPos;
        temp.blackKingPos = rec.blackKingPos;
        temp.epPawnPos = rec.epPawnPos;
        
        return temp;
    }

    public void set(BoardRecord rec) {
        this.board = rec.board;
        this.whiteKingPos = rec.whiteKingPos;
        this.blackKingPos = rec.blackKingPos;
        this.epPawnPos = rec.epPawnPos;
    }

    public boolean tryMove(int from, int to) {
        Piece movingPiece = board[from];
        
        MoveList legal = pieceMoves(from);
        if (!legal.contains(to)) {
            return false;
        }

        boolean dp = (legal.getSpecialMove(SpecialMove.DOUBLE_PUSH) == to);
        boolean ep = (legal.getSpecialMove(SpecialMove.EN_PASSANT) == to);
        // TODO: implement castling, promotion
        // boolean cl = (legal.getSpecialMove(SpecialMove.CASTLE_LONG) == to);
        // boolean cs = (legal.getSpecialMove(SpecialMove.CASTLE_SHORT) == to);


        BoardRecord tempRec = copy(this);
        tempRec.board[to] = movingPiece;
        tempRec.board[from] = new Piece();
        if (movingPiece.type() == PieceType.KING) {
            tempRec.updateKingPositions(movingPiece.colour(), to);
        }

        MoveList[] enemyMoves = tempRec.possibleMoves(movingPiece.oppositeColour());
        int kingSquare = (movingPiece.colour() == PieceColour.WHITE) 
        ? tempRec.whiteKingPos 
        : tempRec.blackKingPos;
        for (MoveList list : enemyMoves) {
            if (list == null) {
                continue;
            }
            if (list.contains(kingSquare)) {
                return false;
            }
        }

        board[to] = movingPiece;
        board[from] = new Piece();
        board[to].setPos(to);
        if (ep) { 
            board[epPawnPos] = new Piece(); 
        }
        if (movingPiece.type() == PieceType.KING) {
            updateKingPositions(movingPiece.colour(), to);
        }
        epPawnPos = (dp) ? to : -1;
        board[to].setMoved(true); 

        return true;
    }

    private void updateKingPositions(PieceColour col, int pos) {
        switch (col) {
            case WHITE:
                whiteKingPos = pos;
                break;
            case BLACK:
                blackKingPos = pos;
                break;
            default:
                break;
        }
    }

    public MoveList pieceMoves(int index) {
        return board[index].getMoves(this);
    }

    public MoveList[] possibleMoves(PieceColour col) {
        MoveList[] moves = new MoveList[16];
        int end = 0;
        for (int i = 0; i < board.length; i++) {
            if (colourAt(i) != col) {
                continue;
            }

            MoveList pieceMoves = new MoveList(30, i);
            pieceMoves.add(pieceMoves(i));
            moves[end] = pieceMoves;
            end++;
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
