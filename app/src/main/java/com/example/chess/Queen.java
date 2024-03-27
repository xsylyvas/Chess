package com.example.chess;
import java.util.ArrayList;
import java.util.List;
public class Queen extends ChessPiece {
    public Queen(String color) {
        super("Queen", color, getQueenImageResource(color));
    }

    private static int getQueenImageResource(String color) {
        if (color.equals("White")) {
            return R.drawable.chessqlt60;  // Зображення білого ферзя
        } else {
            return R.drawable.chessqdt60;  // Зображення чорного ферзя
        }
    }


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
        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);

        // Перевірка чи немає фігур на шляху руху ферзя
        if (currentRow == targetRow || currentCol == targetCol || rowDiff == colDiff) {
            int rowStep = Integer.compare(targetRow, currentRow);
            int colStep = Integer.compare(targetCol, currentCol);

            for (int i = 1; i < Math.max(rowDiff, colDiff); i++) {
                int rowToCheck = currentRow + i * rowStep;
                int colToCheck = currentCol + i * colStep;

                if (board[rowToCheck][colToCheck] != null) {
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

        // Перевірка діагональних ходів
        for (int targetRow = 0; targetRow < 8; targetRow++) {
            for (int targetCol = 0; targetCol < 8; targetCol++) {
                if (isValidMove(currentRow, currentCol, targetRow, targetCol, board, getColor(), false)) {
                    allMoves.add(new Move(currentRow, currentCol, targetRow, targetCol));
                }
            }
        }

        return allMoves;
    }
}
