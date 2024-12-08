package com.ang.Pieces;

public enum PieceColour {
    WHITE,
    BLACK,
    NONE;

    public static PieceColour opposite(PieceColour col) {
        switch (col) {
            case WHITE:
                return PieceColour.BLACK;
            case BLACK:
                return PieceColour.WHITE;
            default:
                return PieceColour.NONE;
        }
    }
}
