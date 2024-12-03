package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.MoveList;

public class King extends Piece {
    private int pos;
    private PieceColour col;
    private boolean moved = false;

    public King(int pos, PieceColour col) {
        this.pos = pos;
        this.col = col;
    }

    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(8);
        PieceColour opCol = this.oppositeColour();
        int[] offsets = new int[]{-9, -8, -7, -1, 1, 7, 8, 9};

        for (int move : offsets) {
            if ((super.inBounds(pos, move)) 
                    && (rec.colourAt(pos + move) == opCol)){
                moves.add(pos + move);
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
        return PieceType.KING;
    }

    @Override
    public PieceColour colour() {
        return col;
    }
}