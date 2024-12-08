package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.Move;
import com.ang.Util.MoveList;

public class Bishop extends Piece {
    public Bishop(int pos, PieceColour col) {
        super(pos, col);
    }

    @Override
    public Piece copy() {
        Bishop out = new Bishop(pos, col);
        out.setMoved(this.hasMoved());
        return out;
    }
    
    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(27);

        int[] offsets = new int[]{-9, -7, 7, 9};
        for (int direction : offsets) {
            int step = 1;
            int stepPos = pos;
            while (step < 8) {
                if ((!super.inBounds(stepPos, direction))
                        || (rec.colourAt(stepPos + direction) == col)) {
                    break;
                }
                if (rec.colourAt(stepPos + direction) == this.oppositeColour()) {
                    moves.add(new Move(this.copy(), pos, stepPos + direction));
                    break;
                }
                
                moves.add(new Move(this.copy(), pos, stepPos + direction));
                stepPos += direction;
                step++;
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
