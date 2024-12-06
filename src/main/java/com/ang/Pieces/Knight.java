package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.Move;
import com.ang.Util.MoveList;

public class Knight extends Piece {
    public Knight(int pos, PieceColour col) {
        super(pos, col);
    }

    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(8);
        int[] offsets = new int[]{-17, -15, -10, -6, 6, 10, 15, 17};

        for (int move : offsets) {
            if ((super.inBounds(pos, move)) 
                    && (rec.colourAt(pos + move) != this.col)) {
                moves.add(new Move(this, pos, pos + move));
            }
        }
        
        return moves;
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