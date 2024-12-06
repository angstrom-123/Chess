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

    // make a move then:
    //     for all moves in the position
    //          make the move
    //          call recurse on this new rec with opposite colour
    //          until depth limit
    //          then record eval at final position
    //          (return eval at depth limit)
    //     if move's eval is better than best eval then record the move and eval
    
    public Move generateMove(BoardRecord rec) {
        Move bestMove = new Move();

        double bestEval = Global.infinity;
        MoveList possibleMoves = rec.possibleMoves(PieceColour.BLACK);
        for (int i = 0; i < possibleMoves.length(); i++) {
            BoardRecord tempRec = rec.copy();
            if (tempRec.tryMove(possibleMoves.at(i))) {
                double eval = recurse(tempRec, PieceColour.WHITE, maxDepth, Global.infinity);
                if (eval > bestEval) {
                    bestEval = eval;
                    bestMove = possibleMoves.at(i);
                }
            }
        }

        return bestMove;
    }

    public double recurse(BoardRecord rec, PieceColour col, int depth, double currentEval) {
        return 0.0;
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
}
