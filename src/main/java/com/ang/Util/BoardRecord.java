package com.ang.Util;

import com.ang.Pieces.*;
import com.ang.Pieces.PieceColour;
import com.ang.Pieces.PieceType;

public class BoardRecord {
    public Piece[] board;
    public int whiteKingPos;
    public int blackKingPos;
    public int epPawnPos;
    public int[] piecePosArr;

    private int piecePosEnd;

    public BoardRecord() {}

    public void prefetchPiecePositions() {
        if (board == null) {
            return;
        }

        piecePosArr = new int[32];
        piecePosEnd = 0;
        for (int i = 0; i < board.length; i++) {
            if (i < 32) {
                piecePosArr[i] = -1;
            }
            if (board[i].type() != PieceType.NONE) {
                piecePosArr[piecePosEnd] = i;
                piecePosEnd++;
            }
        }
    }

    public BoardRecord copy() {
        BoardRecord temp = new BoardRecord();

        temp.board = new Piece[64];
        for (int i = 0; i < this.board.length; i++) {
            temp.board[i] = this.board[i].copy();
        }
        temp.piecePosArr = new int[32];
        for (int i = 0; i < this.piecePosArr.length; i++) {
            temp.piecePosArr[i] = this.piecePosArr[i];
        }
        temp.piecePosEnd = this.piecePosEnd;
        temp.whiteKingPos = this.whiteKingPos;
        temp.blackKingPos = this.blackKingPos;
        temp.epPawnPos = this.epPawnPos;
        
        return temp;
    }

    public boolean tryMove(Move move) {
        if (move.isInvalid()) {
            return false;
        }

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

        for (int i = 0; i < piecePosArr.length; i++) {
            int pos = piecePosArr[i];
            if (pos == -1) {
                break;
            }
            if (tempRec.colourAt(pos) == move.piece().oppositeColour()) {
                MoveList moves = tempRec.board[pos].getMoves(tempRec);
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

        int startIndex = -1;
        int endIndex = -1;
        for (int i = 0; i < piecePosArr.length; i++) {
            if (piecePosArr[i] == -1) {
                break;
            }
            if (piecePosArr[i] == move.from()) {
                startIndex = i;
            }
            if (piecePosArr[i] == move.to()) {
                endIndex = i;
            }
        }

        if (endIndex == -1) {
            piecePosArr[startIndex] = move.to();
        } else {
            piecePosArr[endIndex] = move.to();
            piecePosArr[startIndex] = piecePosArr[piecePosEnd - 1];
            piecePosArr[piecePosEnd - 1] = -1;
            piecePosEnd--;
        }

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
        for (int i = 0; i < piecePosArr.length; i++) {
            int pos = piecePosArr[i];
            if (pos == -1) {
                break;
            }
            if (colourAt(pos) == col) {
                moves.add(pieceMoves(pos));
            }
        }

        return moves;
    }

    public PieceType pieceAt(int pos) {
        if (((pos < 0) || (pos > 63) || board[pos] == new Piece())) {
            return PieceType.NONE;
        }
        return board[pos].type();
    }

    public PieceColour colourAt(int pos) {
        if (((pos < 0) || (pos > 63) || board[pos] == new Piece())) {
            return PieceColour.NONE;
        }
        return board[pos].colour();
    }
}
