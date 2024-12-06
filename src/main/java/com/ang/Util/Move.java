package com.ang.Util;

import com.ang.Pieces.Piece;

public class Move {
    private Piece piece;
    private int from;
    private int to;

    public Move() {};

    public Move(Piece piece, int from, int to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
    }

    public boolean equals(Move move) {
        return ((piece == move.piece()) 
                && (from == move.from()) && (to == move.to()));
    }

    public boolean isInvalid() {
        return ((from == -1) && (to == -1));
    }

    public Piece piece() {
        return piece;
    }

    public int from() {
        return from;
    }

    public int to() {
        return to;
    }

    public static Move invalid() {
        return new Move(new Piece(), -1, -1);
    }
}
