package com.ang.Util;

import com.ang.Pieces.*;
import com.ang.Pieces.PieceColour;
import com.ang.Pieces.PieceType;

public class BoardRecord {
    public Piece[] board;
    public int whiteKingPos;
    public int blackKingPos;
    public int epPawnPos;

    public BoardRecord() {}

    public BoardRecord copy() {
        BoardRecord temp = new BoardRecord();

        temp.board = new Piece[64];
        for (int i = 0; i < temp.board.length; i++) {
            temp.board[i] = this.board[i];
        }
        temp.whiteKingPos = this.whiteKingPos;
        temp.blackKingPos = this.blackKingPos;
        temp.epPawnPos = this.epPawnPos;
        
        return temp;
    }

    public boolean tryMove(Move move) {
        MoveList legal = pieceMoves(move.from());
        if (!legal.contains(move.to())) {
            return false;
        }
        
        boolean dp = (legal.getSpecialMove(SpecialMove.DOUBLE_PUSH).equals(move));
        boolean ep = (legal.getSpecialMove(SpecialMove.EN_PASSANT).equals(move));
        // TODO: implement castling, promotion
        // boolean cl = (legal.getSpecialMove(SpecialMove.CASTLE_LONG).equals(move));
        // boolean cs = (legal.getSpecialMove(SpecialMove.CASTLE_SHORT).equals(move));
        // boolean pr = (legal.getSpecialMove(SpecialMove.PROMOTION).equals(move));

        BoardRecord tempRec = this.copy();
        tempRec.movePiece(move);
        for (int i = 0; i < tempRec.board.length; i++) {
            if (tempRec.colourAt(i) == move.piece().oppositeColour()) {
                MoveList moves = tempRec.board[i].getMoves(tempRec);
                int kingSquare = (move.piece().colour() == PieceColour.WHITE)
                ? tempRec.whiteKingPos
                : tempRec.blackKingPos;

                if (moves.contains(kingSquare)) {
                    return false;
                }
            }
        }

        movePiece(move);
        board[move.to()].setPos(move.to());

        if (ep) {
            board[epPawnPos] = new Piece(); 
        }
        epPawnPos = (dp) ? move.to() : -1;
        board[move.to()].setMoved(true); 

        return true;
    }

    public void movePiece(Move move) {
        board[move.to()] = move.piece();
        board[move.from()] = new Piece();
        move.piece().setMoved(true);

        if (move.piece().type() == PieceType.KING) {
            switch (move.piece().colour()) {
                case WHITE:
                    whiteKingPos = move.to();
                    break;
                case BLACK:
                    blackKingPos = move.to();
                    break;
                default:
                    break;
            }
        }
    }

    public MoveList pieceMoves(int index) {
        return board[index].getMoves(this);
    }


    public MoveList possibleMoves(PieceColour col) {
        MoveList moves = new MoveList(16 * 27);
        for (int i = 0; i < board.length; i++) {
            if (colourAt(i) == col) {
                moves.add(pieceMoves(i));
            }
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
