package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.Move;
import com.ang.Util.MoveList;
import com.ang.Util.SpecialMove;

public class Pawn extends Piece {
    private int dir;

    public Pawn(int pos, PieceColour col) {
        super(pos, col);

        if (col == PieceColour.BLACK) {
            dir = -1;
        } else {
            dir = 1;
        }
    }

    @Override
    public Piece copy() {
        Pawn out = new Pawn(pos, col);
        out.setMoved(this.hasMoved());
        return out;
    }

    @Override
    public MoveList getMoves(BoardRecord rec) {
        MoveList moves = new MoveList(6);
        PieceColour opCol = this.oppositeColour();
        int move;
        
        move = -8 * dir;
        if (rec.pieceAt(pos + move) == PieceType.NONE) {
            // single push
            if (super.inBounds(pos, move)) { 
                if ((pos < 8) || (pos >55)) {
                    moves.addSpec(new Move(this.copy(), pos, pos + move), SpecialMove.PROMOTION);
                }
                moves.add(new Move(this.copy(), pos, pos + move)); 
            }

            if (!hasMoved()) {
                move = -16 * dir;
                if (rec.pieceAt(pos + move) == PieceType.NONE) {
                    // double push
                    if (super.inBounds(pos, move)) { 
                        moves.addSpec(new Move(this.copy(), pos, pos + move), SpecialMove.DOUBLE_PUSH); 
                    }
                }
            }
            
        }
        
        move = (-8 * dir) - 1;
        if (rec.colourAt(pos + move) == opCol) {
            // take left
            if (super.inBounds(pos, move)) { 
                moves.add(new Move(this.copy(), pos, pos + move)); 
            }
        } else if (rec.pieceAt(pos + move) == PieceType.NONE) {
            if (rec.epPawnPos == pos -1) {
                // en passant left
                if (super.inBounds(pos, move)) { 
                    moves.addSpec(new Move(this.copy(), pos, pos + move), SpecialMove.EN_PASSANT); 
                }
            }
        }

        move = (-8 * dir) + 1;
        if (rec.colourAt(pos + move) == opCol) { 
            // take right
            if (super.inBounds(pos, move)) { 
                moves.add(new Move(this.copy(), pos, pos + move)); 
            }
        } else {
            if (rec.epPawnPos == pos + 1) {
                // en passant right
                if (super.inBounds(pos, move)) { 
                    moves.addSpec(new Move(this.copy(), pos, pos + move), SpecialMove.EN_PASSANT); 
                }
            }
        }

        return moves;
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }

    @Override
    public PieceColour colour() {
        return col;
    }
}
