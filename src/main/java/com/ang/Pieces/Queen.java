package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.MoveList;

public class Queen extends Piece {
    public Queen(int pos, PieceColour col) {
        super(pos, col);
    }
    
    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(30);
        PieceColour opCol = this.oppositeColour();

        int[] offsets = new int[]{-9, -8, -7, -1, 1, 7, 8, 9};
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
        return PieceType.QUEEN;
    }

    @Override
    public PieceColour colour() {
        return col;
    }
}
