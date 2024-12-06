package com.ang.Pieces;

public enum PieceType {
    PAWN ("/PawnSprite.png", 100.0),
    KNIGHT ("/KnightSprite.png", 300.0),
    BISHOP ("/BishopSprite.png", 300.0),
    ROOK ("/RookSprite.png", 500.0),
    QUEEN ("/QueenSprite.png", 900.0),
    KING ("/KingSprite.png", 0.0),
    NONE;

    private String spritePath;
    private double value;

    private PieceType(String spritePath, double value) {
        this.spritePath = spritePath;
        this.value = value;
    }

    private PieceType() {
        this.spritePath = null;
        this.value = 0.0;
    }

    public String path() {
        return spritePath;
    }

    public double value() {
        return value;
    }
}
