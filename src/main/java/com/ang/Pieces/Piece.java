package com.ang.Pieces;

import com.ang.BoardRecord;
import com.ang.MoveList;

public class Piece {
    public MoveList getMoves(BoardRecord rec) {
        return new MoveList(0);
    }

    public boolean hasMoved() {
        return false;
    }

    public PieceType type() {
        return PieceType.NONE;
    }

    public PieceColour colour() {
        return PieceColour.NONE;
    }

    public PieceColour oppositeColour() {
        switch (this.colour()) {
            case WHITE:
                return PieceColour.BLACK;
            case BLACK:
                return PieceColour.WHITE;
            default:
                return null;
        }
    }

    public boolean inBounds(int pos, int offset) {
        int posX = pos % 8;
        int posY = (int)Math.floor(pos / 8);
        int offsetX = offset % 8;
        int offsetY = (int)Math.floor(offset / 8);

        if ((posX + offsetX > -1) && (posX + offsetX < 8)
                && (posY + offsetY > -1) && (posY + offsetY < 8)) {
            return true;
        }
        return false;
    }
}
