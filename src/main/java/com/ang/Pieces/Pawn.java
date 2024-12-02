package com.ang.Pieces;

import com.ang.BoardRecord;
import com.ang.MoveList;

public class Pawn extends Piece {
    private int pos;
    private int dir;
    private PieceColour col;
    private boolean moved = false;

    public Pawn(int pos, PieceColour col) {
        this.pos = pos;
        this.col = col;

        if (col == PieceColour.BLACK) {
            dir = -1;
        } else {
            dir = 1;
        }
    }

    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(6);
        PieceColour opCol = this.oppositeColour();
        int move;
        
        move = -8 * dir;
        if (rec.pieceAt(pos + move) == null) {
            // single push
            if (super.inBounds(pos, move)) { moves.add(pos + move); }

            move = -16 * dir;
            if (rec.pieceAt(pos + move) == null) {
                // double push
                if (super.inBounds(pos, move)) { moves.add(pos + move); }
            }
        }
        
        move = -9 * dir;
        if (rec.colourAt(pos + move) == opCol) {
            // take left
            if (super.inBounds(pos, move)) { moves.add(pos + move); }
        } else if (rec.pieceAt(pos + move) == null) {
            if (rec.epPawnPos == pos -1) {
                // en passant left
                if (super.inBounds(pos, move)) { moves.addSpec(pos + move); }
            }
        }

        move = -7 * dir;
        if (rec.colourAt(pos + move) == opCol) { 
            // take right
            if (super.inBounds(pos, move)) { moves.add(pos + move); }
        } else {
            if (rec.epPawnPos == pos + 1) {
                // en passant right
                if (super.inBounds(pos, move)) { moves.addSpec(pos + move); }
            }
        }

        return moves;
    }

    @Override
    public boolean hasMoved() {
        return moved;
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    @Override
    public PieceColour colour() {
        return col;
    }
}
