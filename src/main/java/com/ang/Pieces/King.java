package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.Move;
import com.ang.Util.MoveList;
import com.ang.Util.SpecMove;

public class King extends Piece {
    public King(int pos, PieceColour col) {
        super(pos, col);
    }

    @Override
    public Piece copy() {
        King out = new King(pos, col);
        out.setMoved(this.hasMoved());
        return out;
    }

    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(10);
        int[] offsets = new int[]{-9, -8, -7, -1, 1, 7, 8, 9};

        for (int move : offsets) {
            if ((super.inBounds(pos, move)) 
                    && (rec.colourAt(pos + move) != col)){
                moves.add(new Move(this.copy(), pos, pos + move));
            }
        }

        if (hasMoved()) {
            return moves;
        }

        // castle short
        int[] shortOffsets = new int[]{1, 2};
        boolean canShort = true;
        for (int offset : shortOffsets) {
            if (rec.pieceAt(pos + offset) != PieceType.NONE) {
                canShort = false;
                break;
            }
        }
        if (canShort) {
            int rookIndex = (col == PieceColour.WHITE) ? 63 : 7;
            if (!rec.board[rookIndex].hasMoved()) {
                moves.addSpec(new Move(this.copy(), pos, pos + 2), 
                        SpecMove.CASTLE_SHORT);
            }
        }

        // castle long
        int[] longOffsets = new int[]{-1, -2, -3};
        boolean canLong = true;
        for (int offset : longOffsets) {
            if (rec.pieceAt(pos + offset) != PieceType.NONE) {
                canLong = false;
                break;
            }
        }
        if (canLong) {
            int rookIndex = (col == PieceColour.WHITE) ? 56 : 0;
            if (!rec.board[rookIndex].hasMoved()) {
                moves.addSpec(new Move(this.copy(), pos, pos - 2), 
                        SpecMove.CASTLE_LONG);
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