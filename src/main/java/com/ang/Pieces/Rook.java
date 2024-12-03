package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.MoveList;

public class Rook extends Piece {
    private int pos;
    private PieceColour col;
    private boolean moved = false;

    public Rook(int pos, PieceColour col) {
        this.pos = pos;
        this.col = col;
    }
    
    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(30);
        PieceColour opCol = this.oppositeColour();

        int[] offsets = new int[]{-8, -1, 1, 8};
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
    public boolean hasMoved() {
        return moved;
    }

    @Override
    public PieceType type() {
        return PieceType.ROOK;
    }

    @Override
    public PieceColour colour() {
        return col;
    }
}
