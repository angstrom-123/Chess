package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.MoveList;

public class Bishop extends Piece {
    public Bishop(int pos, PieceColour col) {
        super(pos, col);
    }
    
    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(30, pos);
        PieceColour opCol = this.oppositeColour();

        int[] offsets = new int[]{-9, -7, 7, 9};
        for (int direction : offsets) {
            int step = 1;
            while (step < 8) {
                int move = direction * step;
                if ((!super.inBounds(pos, move)
                        || (rec.colourAt(pos + move) != opCol))) {
                    break;
                }
                
                moves.add(pos + move);
            }
        }

        return moves;
    }

    @Override
    public PieceType type() {
        return PieceType.BISHOP;
    }

    @Override
    public PieceColour colour() {
        return col;
    }
}
