package com.ang.Util;

import com.ang.Pieces.Bishop;
import com.ang.Pieces.King;
import com.ang.Pieces.Knight;
import com.ang.Pieces.Pawn;
import com.ang.Pieces.Piece;
import com.ang.Pieces.PieceColour;
import com.ang.Pieces.Queen;
import com.ang.Pieces.Rook;

public class FENReader {
    public static Piece[] readFEN(String fen) {
        Piece[] board = new Piece[64];
        for (int i = 0; i < 64; i++) {
            board[i] = new Piece();
        }
        int index = 0;

        char[] chars = fen.toCharArray();
        for (char c : chars) {
            switch (c) {
                case '1':
                    break;
                case '2':
                    index += 1;
                    break;
                case '3':
                    index += 2;
                    break;
                case '4':
                    index += 3;
                    break;
                case '5':
                    index += 4;
                    break;
                case '6':
                    index += 5;
                    break;
                case '7':
                    index += 6;
                    break;
                case '8':
                    index += 7;
                    break;
                case 'p':
                    board[index] = new Pawn(index, PieceColour.BLACK);
                    break;
                case 'P':
                    board[index] = new Pawn(index, PieceColour.WHITE);
                    break;
                case 'n':
                    board[index] = new Knight(index, PieceColour.BLACK);
                    break;
                case 'N':
                    board[index] = new Knight(index, PieceColour.WHITE);
                    break;    
                case 'b':
                    board[index] = new Bishop(index, PieceColour.BLACK);
                    break;
                case 'B':
                    board[index] = new Bishop(index, PieceColour.WHITE);
                    break;
                case 'r':
                    board[index] = new Rook(index, PieceColour.BLACK);
                    break;
                case 'R':
                    board[index] = new Rook(index, PieceColour.WHITE);
                    break;
                case 'q':
                    board[index] = new Queen(index, PieceColour.BLACK);
                    break;
                case 'Q':
                    board[index] = new Queen(index, PieceColour.WHITE);
                    break;
                case 'k':
                    board[index] = new King(index, PieceColour.BLACK);
                    break;
                case 'K':
                    board[index] = new King(index, PieceColour.WHITE);
                    break;
                default:
                    break;
            }
            index++;
        }

        return board;
    }
}
