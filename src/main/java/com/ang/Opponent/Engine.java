package com.ang.Opponent;

import com.ang.Global;

import com.ang.Pieces.*;
import com.ang.Util.BoardRecord;
import com.ang.Util.Move;
import com.ang.Util.MoveList;

public class Engine {   
    private int maxDepth;

    public Engine(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Move generateMove(BoardRecord rec) {
        Move bestMove = Move.invalid();
        PieceColour moveCol = PieceColour.BLACK;

        double bestEval = -Global.infinity;

        MoveList possibleMoves = rec.possibleMoves(moveCol);
        for (int i = 0; i < possibleMoves.length() - 1; i++) {
            BoardRecord tempRec = rec.copy();
            Move tempMove = possibleMoves.at(i);
            
            if (tempRec.tryMove(tempMove)) {
                double eval = -negaMax(tempRec, PieceColour.opposite(moveCol), maxDepth);

                if (eval > bestEval) {
                    bestEval = eval;
                    bestMove = tempMove;
                }
            }
        }

        // TODO: implement checkmate
        if (bestMove.isInvalid()) {
            System.out.println("couldn't find move");
        }
        return bestMove;
    }

    public double negaMax(BoardRecord rec, PieceColour col, int depth) {
        if (depth == 0) {
            double eval = evaluate(rec);
            return eval;
        }

        double max = -Global.infinity;
        MoveList possibleMoves = rec.possibleMoves(col);
        for (int i = 0; i < possibleMoves.length() - 1; i++) {
            BoardRecord tempRec = rec.copy();
            Move tempMove = possibleMoves.at(i);
    
            if (tempRec.tryMove(tempMove)) {
                double eval = negaMax(tempRec, PieceColour.opposite(col), depth - 1);
                if (eval > max) {
                    max = eval;
                }
            }
        }

        return max;
    }

    private double evaluate(BoardRecord rec) {
        double eval = 0.0;

        for (int i = 0; i < rec.board.length; i++) {
            switch (rec.colourAt(i)) {
                case WHITE:
                    eval += rec.pieceAt(i).value();
                    break;
                case BLACK:
                    eval -= rec.pieceAt(i).value();
                    break;
                default:
                    break;
            }
        }

        return eval;
    }

    // for debugging
    // private void displayBoard(BoardRecord rec) {
    //     for (int i = 0; i < rec.board.length; i++) {
    //         if (i % 8 == 0) {
    //             System.out.println();
    //         }
    //         switch (rec.pieceAt(i)) {
    //             case PAWN:
    //                 if (rec.colourAt(i) == PieceColour.WHITE) {
    //                     System.out.print('P');
    //                 } else {
    //                     System.out.print('p');
    //                 }
    //                 break;
    //             case KNIGHT:
    //                 if (rec.colourAt(i) == PieceColour.WHITE) {
    //                     System.out.print('N');
    //                 } else {
    //                     System.out.print('n');
    //                 }
    //                 break;
    //             case BISHOP:
    //                 if (rec.colourAt(i) == PieceColour.WHITE) {
    //                     System.out.print('B');
    //                 } else {
    //                     System.out.print('b');
    //                 }
    //                 break;
    //             case ROOK:
    //                 if (rec.colourAt(i) == PieceColour.WHITE) {
    //                     System.out.print('R');
    //                 } else {
    //                     System.out.print('r');
    //                 }
    //                 break;
    //             case QUEEN:
    //                 if (rec.colourAt(i) == PieceColour.WHITE) {
    //                     System.out.print('Q');
    //                 } else {
    //                     System.out.print('q');
    //                 }
    //                 break;
    //             case KING:
    //                 if (rec.colourAt(i) == PieceColour.WHITE) {
    //                     System.out.print('K');
    //                 } else {
    //                     System.out.print('k');
    //                 }
    //                 break;
    //             default:
    //                 System.out.print(' ');
    //                 break;

    //         }
    //     }
    //     System.out.println();
    //     System.out.println("----------");
    // }
}