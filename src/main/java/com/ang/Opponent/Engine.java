package com.ang.Opponent;

import com.ang.Global;

import com.ang.Pieces.*;
import com.ang.Util.BoardRecord;
import com.ang.Util.Move;
import com.ang.Util.MoveList;

// TODO : optimization:
//      - Increase search depth when there are fewer pieces on the board
//      - iterative deepening
//      - history heuristic
//      - killer moves
//      - quiescent search
//      - pawn position evaluation
//      - negascout

public class Engine {   
    private int maxDepth;
    private boolean isEndgame = false;

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
                double eval = -alphaBeta(tempRec, PieceColour.opposite(moveCol),
                        -Global.infinity, Global.infinity, maxDepth);

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

    public double alphaBeta(BoardRecord rec, PieceColour col, 
            double alpha, double beta, int depth) {
        if (depth == 0) {
            double eval = evaluate(rec);
            return eval;
        }

        MoveList possibleMoves = rec.possibleMoves(col);
        if (col == PieceColour.WHITE) { // max
            double max = -Global.infinity;
            for (int i = 0; i < possibleMoves.length() - 1; i++) {
                BoardRecord tempRec = rec.copy();
                Move tempMove = possibleMoves.at(i);

                if (tempRec.tryMove(tempMove)) {
                    double eval = alphaBeta(tempRec, PieceColour.BLACK,
                            alpha, beta, depth - 1);
                    max = Math.max(eval, max);
                    alpha = Math.max(alpha, max);
                    if (max >= beta) {
                        break;
                    }
                }
            }
            return max;
        } else {
            double min = Global.infinity;
            for (int i = 0; i < possibleMoves.length() - 1; i++) {
                BoardRecord tempRec = rec.copy();
                Move tempMove = possibleMoves.at(i);

                if (tempRec.tryMove(tempMove)) {
                    double eval = alphaBeta(tempRec, PieceColour.WHITE,
                            alpha, beta, depth - 1);
                    min = Math.min(eval, min);
                    beta = Math.min(beta, min);
                    if (min <= alpha) {
                        break;
                    }
                }
            }
            return min;
        }
    }

    private double pieceValue(BoardRecord rec, int index) {
        double value = 0.0;
        int heatmapIndex = (rec.colourAt(index) == PieceColour.WHITE)
        ? index
        : 63 - index;

        switch (rec.pieceAt(index)) {
            case PieceType.PAWN:
                value = 100.0 + Heatmap.pawnMap[heatmapIndex];
                break;
            case PieceType.KNIGHT:
                value = 320.0 + Heatmap.pawnMap[heatmapIndex];
                break;
            case PieceType.BISHOP:
                value = 330.0 + Heatmap.pawnMap[heatmapIndex];
                break;
            case PieceType.ROOK:
                value = 500.0 + Heatmap.pawnMap[heatmapIndex];
                break;
            case PieceType.QUEEN:
                value = 900.0 + Heatmap.pawnMap[heatmapIndex];
                break;
            case PieceType.KING:
                double[] heatmap = isEndgame 
                ? Heatmap.kingEndMap
                : Heatmap.kingStartMap;
                value = 20000.0 + heatmap[heatmapIndex];
                break;
            default:
                value = 0.0;
        }

        return (rec.colourAt(index) == PieceColour.WHITE) ? value : -value;
    }

    private double evaluate(BoardRecord rec) {
        double eval = 0.0;

        int minorPieceCount = 0;
        for (int i = 0; i < rec.board.length; i++) {
            eval += pieceValue(rec, i);
            PieceType p = rec.pieceAt(i);
            if ((p == PieceType.KNIGHT) || (p == PieceType.BISHOP)) {
                minorPieceCount++;
            }
        }

        if (minorPieceCount < 2) {
            isEndgame = true;
        }

        return eval;
    }
}