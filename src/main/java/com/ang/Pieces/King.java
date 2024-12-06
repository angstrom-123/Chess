package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.Move;
import com.ang.Util.MoveList;

public class King extends Piece {
    public King(int pos, PieceColour col) {
        super(pos, col);
    }

    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(8);
        int[] offsets = new int[]{-9, -8, -7, -1, 1, 7, 8, 9};

        for (int move : offsets) {
            if ((super.inBounds(pos, move)) 
                    && (rec.colourAt(pos + move) != col)){
                moves.add(new Move(this, pos, pos + move));
            }
        }
        
        return moves;
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