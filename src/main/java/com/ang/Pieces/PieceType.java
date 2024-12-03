package com.ang.Pieces;

public enum PieceType {
    PAWN ("/PawnSprite.png"),
    KNIGHT ("/KnightSprite.png"),
    BISHOP ("/BishopSprite.png"),
    ROOK ("/RookSprite.png"),
    QUEEN ("/QueenSprite.png"),
    KING ("/KingSprite.png"),
    NONE;

    private String spritePath;

    private PieceType(String spritePath) {
        this.spritePath = spritePath;
    }

    private PieceType() {
        this.spritePath = null;
    }

    public String path() {
        return spritePath;
    }
}
