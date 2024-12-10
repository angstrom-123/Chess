package com.ang.Util;

import com.ang.Pieces.*;
import com.ang.Pieces.PieceColour;
import com.ang.Pieces.PieceType;

public class BoardRecord {
    public Piece[] board;
    public int whiteKingPos;
    public int blackKingPos;
    public int epPawnPos;
    public int minorPieceCount;
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
        temp.whiteKingPos = this.whiteKingPos;
        temp.blackKingPos = this.blackKingPos;
        temp.epPawnPos = this.epPawnPos;
        temp.minorPieceCount = this.minorPieceCount;
        temp.piecePosEnd = this.piecePosEnd;
        
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
        
        BoardRecord tempRec = this.copy();
        tempRec.movePiece(move, legal);

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

        movePiece(move, legal);

        return true;
    }

    public void movePiece(Move move, MoveList legalMoves) {
        PieceType taken = board[move.to()].type();
        if ((taken == PieceType.KNIGHT) || (taken == PieceType.BISHOP)) {
            minorPieceCount--;
        }

        board[move.to()] = move.piece();
        board[move.to()].setMoved(true);
        board[move.to()].setPos(move.to());
        board[move.from()] = new Piece();

        if (legalMoves.getSpecMove(SpecMove.EN_PASSANT).equals(move)) {
            board[epPawnPos] = new Piece(); 
        }
        if (legalMoves.getSpecMove(SpecMove.CASTLE_SHORT).equals(move)) {
            int rookPos = move.from() + 3;
            board[rookPos] = new Piece();
            board[rookPos - 2] = new Rook(rookPos - 2, move.piece().colour());
            board[rookPos - 2].setMoved(true);
            for (int i = 0; i < piecePosEnd; i++) {
                if (piecePosArr[i] == rookPos) {
                    piecePosArr[i] = rookPos - 2;
                }
            }
        }
        if (legalMoves.getSpecMove(SpecMove.CASTLE_LONG).equals(move)) {
            int rookPos = move.from() - 4;
            board[rookPos] = new Piece();
            board[rookPos + 3] = new Rook(rookPos + 3, move.piece().colour());
            board[rookPos + 3].setMoved(true);
            for (int i = 0; i < piecePosEnd; i++) {
                if (piecePosArr[i] == rookPos) {
                    piecePosArr[i] = rookPos + 3;
                }
            }
        }
        if ((move.piece().type() == PieceType.PAWN) // promotion
                && ((move.to() < 8) || (move.to() > 55))) {
            board[move.to()] = new Queen(move.to(), move.piece().colour());
            board[move.to()].setMoved(true);
        }

        epPawnPos = (legalMoves.getSpecMove(SpecMove.DOUBLE_PUSH).equals(move)) 
        ? move.to() 
        : -1;

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
