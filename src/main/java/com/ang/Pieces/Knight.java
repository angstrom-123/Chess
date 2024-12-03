package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.MoveList;

public class Knight extends Piece {
    private int pos;
    private PieceColour col;
    private boolean moved = false;

    public Knight(int pos, PieceColour col) {
        this.pos = pos;
        this.col = col;
    }

    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(8);
        PieceColour opCol = this.oppositeColour();
        int[] offsets = new int[]{-17, -15, -10, -6, 6, 10, 15, 17};

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
        return PieceType.KNIGHT;
    }

    @Override
    public PieceColour colour() {
        return col;
    }
}