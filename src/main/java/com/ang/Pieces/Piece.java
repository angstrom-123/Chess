package com.ang.Pieces;

import com.ang.Util.BoardRecord;
import com.ang.Util.MoveList;

public class Piece {
    protected int pos;
    protected PieceColour col;
    protected boolean moved = false;

    public Piece() {};

    public Piece(int pos, PieceColour col) {
        this.pos = pos;
        this.col = col;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public void setPos(int val) {
        this.pos = val;
    }

    public MoveList getMoves(BoardRecord rec) {
        return new MoveList(0, -1);
    }

    public boolean hasMoved() {
        return this.moved;
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
                return PieceColour.NONE;
        }
    }

    public boolean inBounds(int pos, int offset) {
        if ((pos + offset > 63) || (pos + offset) < 0) {
            return false;
        }

        int posX = pos % 8;
        int posY = (int)Math.floor(pos / 8);

        int offsetX = (pos + offset) % 8;
        int offsetY = (int)Math.floor((pos + offset) / 8);

        int deltaX = offsetX - posX;
        int deltaY = offsetY - posY;

        // if the end coord is outside of a 5x5 grid centred on start, OOB
        if ((Math.abs(deltaX) > 2) || (Math.abs(deltaY) > 2)) {
            return false;
        }

        return true;
    }
}
