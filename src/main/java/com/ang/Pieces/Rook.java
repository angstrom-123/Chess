package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.Move;
import com.ang.Util.MoveList;

public class Rook extends Piece {
    public Rook(int pos, PieceColour col) {
        super(pos, col);
    }

    @Override
    public Piece copy() {
        Rook out = new Rook(pos, col);
        out.setMoved(this.hasMoved());
        return out;
    }
    
    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(27);

        int[] offsets = new int[]{-8, -1, 1, 8};
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
        return PieceType.ROOK;
    }

    @Override
    public PieceColour colour() {
        return col;
    }
}
