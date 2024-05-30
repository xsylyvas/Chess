package com.example.chess;
import java.util.ArrayList;
import java.util.List;
public class Rook extends ChessPiece {
    public Rook(String color) {
        super("Rook", color, getRookImageResource(color));
    }

    private static int getRookImageResource(String color) {
        if (color.equals("White")) {
            return R.drawable.chessrlt60;  // Зображення білої тури
        } else {
            return R.drawable.chessrdt60;  // Зображення чорної тури
        }
    }//https://drive.google.com/file/d/144cooxRMo37skIr99d1Dcuwu6qvZ83dv/view?usp=sharing


    @Override
    public boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol, ChessPiece[][] board,
                               String currentPlayer,boolean checkInisCheck) {
        if(!((targetRow >= 0 && targetRow < 8 && targetCol >= 0 && targetCol < 8)
                && (board[targetRow][targetCol] == null ||
                !board[targetRow][targetCol].getColor().equals(board[currentRow][currentCol].getColor()))))return false;
        if (!checkInisCheck)
        {
            Chessboard tmp = new Chessboard(board);
            tmp.movePiece(currentRow,currentCol,targetRow,targetCol);

            if( tmp.isCheck(currentPlayer))return  false;
        }

        if (currentRow == targetRow) {
            int minCol = Math.min(currentCol, targetCol);
            int maxCol = Math.max(currentCol, targetCol);

            for (int col = minCol + 1; col < maxCol; col++) {
                if (board[currentRow][col] != null) {
                    return false; // Є фігура на шляху руху
                }
            }
            return true;
        } else if (currentCol == targetCol) {
            int minRow = Math.min(currentRow, targetRow);
            int maxRow = Math.max(currentRow, targetRow);

            for (int row = minRow + 1; row < maxRow; row++) {
                if (board[row][currentCol] != null) {
                    return false; // Є фігура на шляху руху
                }
            }
            return true;
        }

        return false;
    }
    @Override
    public List<Move> getAllMoves(int currentRow, int currentCol, ChessPiece[][] board) {
        List<Move> allMoves = new ArrayList<>();

        // Перевірка горизонтальних ходів
        for (int targetCol = 0; targetCol < 8; targetCol++) {
            if (isValidMove(currentRow, currentCol, currentRow, targetCol, board, getColor(), false)) {
                allMoves.add(new Move(currentRow, currentCol, currentRow, targetCol));
            }
        }

        // Перевірка вертикальних ходів
        for (int targetRow = 0; targetRow < 8; targetRow++) {
            if (isValidMove(currentRow, currentCol, targetRow, currentCol, board, getColor(), false)) {
                allMoves.add(new Move(currentRow, currentCol, targetRow, currentCol));
            }
        }

        return allMoves;
    }
}
