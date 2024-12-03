package com.ang;

import com.ang.Graphics.Renderer;
import com.ang.Pieces.Bishop;
import com.ang.Pieces.King;
import com.ang.Pieces.Knight;
import com.ang.Pieces.Pawn;
import com.ang.Pieces.Piece;
import com.ang.Pieces.PieceColour;
import com.ang.Pieces.PieceType;
import com.ang.Pieces.Queen;
import com.ang.Pieces.Rook;
import com.ang.Util.BoardRecord;
import com.ang.Util.MoveList;

public class Game implements GameInterface {
    final static int SQUARE_SIZE = 45;
    final static double RENDER_SCALE = 1.2;

    private BoardRecord currentRec;
    private PieceColour colToMove = PieceColour.WHITE;
    private Renderer renderer;

    public Game() {
        Piece[] b = initBoard("rnbqkbnrpppppppp8888PPPPPPPPRNBQKBNR");
        currentRec = new BoardRecord(b, 60, 4, -1);

        showBoard();

        renderer = new Renderer(SQUARE_SIZE, RENDER_SCALE, this);
        renderer.drawAllSprites(currentRec);
    }

    private Piece[] initBoard(String fen) {
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

    private void showBoard() {
        System.out.println();
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                int index = y * 8 + x;
                PieceColour col = currentRec.board[index].colour();
                PieceType typ = currentRec.board[index].type();
                char out;
                switch (typ) {
                    case PAWN:
                        out = 'p';
                        break;
                    case KNIGHT:
                        out = 'n';
                        break;
                    case BISHOP:
                        out = 'b';
                        break;
                    case ROOK:
                        out = 'r';
                        break;
                    case QUEEN:
                        out = 'q';
                        break;
                    case KING:
                        out = 'k';
                        break;
                    default:
                        out = ' ';
                        break;
                }

                if (col == PieceColour.WHITE) {
                    out = Character.toUpperCase(out);
                }

                System.out.print(out);
            }
            System.out.println();
        }
    }

    public void mouseClick(int x, int y) {
        double actualSquareSize = Math.round(SQUARE_SIZE * RENDER_SCALE);
        int xCoord = (int)Math.floor((double)x / actualSquareSize);
        int yCoord = (int)Math.floor((double)y / actualSquareSize);
        int index = yCoord * 8 + xCoord;   

        MoveList moves = currentRec.possibleMoves(index);
        for (int i = 0; i < moves.length() - 1; i++) {
            System.out.println(moves.at(i));
        }
        System.out.println();

        renderer.drawBoard();  
        renderer.highlightSquare(xCoord, yCoord); 
        renderer.drawAllSprites(currentRec);  
    }

    public boolean makeMove() {
        // TODO: implement move test and move make
        return false;
    }
}
