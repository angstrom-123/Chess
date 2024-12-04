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
    
    public BoardRecord(BoardRecord rec) {
        set(rec);
    }

    public void set(BoardRecord rec) {
        this.board = rec.board;
        this.whiteKingPos = rec.whiteKingPos;
        this.blackKingPos = rec.blackKingPos;
        this.epPawnPos = rec.epPawnPos;
    }

    public boolean tryMove(int from, int to) {
        MoveList legal = pieceMoves(from);
        if (!legal.contains(to)) {
            return false;
        }

        boolean dp = (legal.getSpecialMove(SpecialMove.DOUBLE_PUSH) == to);
        boolean ep = (legal.getSpecialMove(SpecialMove.EN_PASSANT) == to);
        // TODO: implement castling, promotion
        // boolean cl = (legal.getSpecialMove(SpecialMove.CASTLE_LONG) == to);
        // boolean cs = (legal.getSpecialMove(SpecialMove.CASTLE_SHORT) == to);

        PieceColour opCol = board[from].oppositeColour();
        int kingSquare = (opCol == PieceColour.BLACK) ? whiteKingPos : blackKingPos;

        BoardRecord tempRec = new BoardRecord(this);

        tempRec.board[to] = board[from];
        tempRec.board[from] = new Piece();
        if (ep) {
            tempRec.board[epPawnPos] = new Piece();
        }

        MoveList[] enemyMoves = tempRec.possibleMoves(opCol);
        for (MoveList list : enemyMoves) {
            if (list.contains(kingSquare)) {
                return false;
            }
        }

        board[to] = board[from];
        board[from] = new Piece();
        board[to].setPos(to);
        if (ep) { 
            board[epPawnPos] = new Piece(); 
        }
        if (pieceAt(to) == PieceType.KING) {
            switch (opCol) {
                case BLACK:
                    whiteKingPos = to;
                    break;
                case WHITE:
                    blackKingPos = to;
                    break;
                default:
                    break;
            }
        }
        epPawnPos = (dp) ? to : -1;
        board[to].setMoved(true); 

        return true;
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
