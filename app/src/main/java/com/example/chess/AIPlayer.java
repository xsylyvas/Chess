package com.example.chess;

import java.util.List;
import java.util.Objects;

public class AIPlayer {
    private static String color;
    private static String enemyColor;
    private static int MAX_DEPTH = 2;  // Глибина аналізу для алгоритму мінімаксу
    public AIPlayer(String color,int level) {
        AIPlayer.color = color;
        MAX_DEPTH = level;
        if(Objects.equals(color, "White")) enemyColor ="Black";
        else enemyColor = "White";

    }

    public String getColor() {
        return color;
    }




    public static Move chooseBestMove(Chessboard chessboard, String currentPlayer) {

        List<Move> availableMoves = chessboard.getAllMoves(currentPlayer);
        Move bestMove = null;
        int bestScore = Integer.MIN_VALUE;
        int alpha = Integer.MIN_VALUE;
        int beta = Integer.MAX_VALUE;

        for (Move move : availableMoves) {
            Chessboard tempBoard = new Chessboard(chessboard.getBoard());
            tempBoard.getPiece(move.getFromRow(), move.getFromCol()).markMoved();
            tempBoard.movePiece(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());

            int score = minimax(tempBoard, MAX_DEPTH - 1, alpha, beta, false);

            if (score > bestScore) {
                bestScore = score;
                bestMove = move;
            }

            alpha = Math.max(alpha, bestScore);
        }

        return bestMove;
    }

    private static int minimax(Chessboard board, int depth, int alpha, int beta, boolean maximizingPlayer) {
        List<Move> availableMoves = board.getAllMoves(maximizingPlayer ? color : enemyColor);
        if(availableMoves.isEmpty() && !maximizingPlayer)return 1000;
        if (depth == 0 ) {
            return (int) (evaluateMaterial(board)*3 + pieceDevelopment(board));
        }



        if (maximizingPlayer) {
            int maxEval = Integer.MIN_VALUE;
            for (Move move : availableMoves) {
                Chessboard tempBoard = new Chessboard(board.getBoard());
                board.getPiece(move.getFromRow(), move.getFromCol()).markMoved();
                tempBoard.movePiece(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
                int eval = minimax(tempBoard, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            int minEval = Integer.MAX_VALUE;
            for (Move move : availableMoves) {
                Chessboard tempBoard = new Chessboard(board.getBoard());
                board.getPiece(move.getFromRow(), move.getFromCol()).markMoved();
                tempBoard.movePiece(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());
                int eval = minimax(tempBoard, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    private static int evaluateMaterial(Chessboard board) {
        int whiteMaterial = 0;
        int blackMaterial = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board.getPiece(i, j);
                if (piece != null) {
                    int pieceValue = getPieceValue(piece);
                    if (piece.getColor().equals(color)) {

                        blackMaterial += pieceValue;

                    } else {
                        whiteMaterial += pieceValue;
                    }
                }
            }
        }

        return blackMaterial - whiteMaterial;
    }

    private static int pieceDevelopment(Chessboard board) {
        int development = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board.getPiece(i, j);
                if (piece != null) {
                    if (!piece.getColor().equals(color)) {
                        development += (i < 5) ? 0 : 1;
                        development += (j > 1 && j < 6) ? 0 : 1;
                    } else {
                        development += (i > 2) ? 1 : 0;
                        development += ((j > 1 && j < 6)) ? 1 : 0;
                    }
                }
            }
        }

        return development;
    }

    private static int getPieceValue(ChessPiece piece) {
        switch (piece.getName()) {
            case "Pawn":
                return 1;
            case "Knight":
                return 3;
            case "Bishop":
                return 3;
            case "Rook":
                return 5;
            case "Queen":
                return 9;
            case "King":
                return 100;
            default:
                return 0;
        }
    }
}
