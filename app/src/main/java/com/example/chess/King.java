package com.example.chess;
import java.util.ArrayList;
import java.util.List;
public class King extends ChessPiece {
    public King(String color) {
        super("King", color, getKingImageResource(color));
    }

    private static int getKingImageResource(String color) {
        if (color.equals("White")) {
            return R.drawable.chessklt60;  // Зображення білого короля
        } else {
            return R.drawable.chesskdt60;  // Зображення чорного короля
        }
    }

    // Додайте логіку для правил руху короля, якщо потрібно

    @Override
    public boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol,ChessPiece[][] board,
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
        // Рух короля дійсний, якщо він переміщується на одну клітинку у будь-якому напрямку
        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);
       if ( currentRow == targetRow && currentCol+2==targetCol&& canCastleKingside(currentRow, currentCol, board))return true;
        if (currentRow == targetRow && currentCol-2==targetCol&&canCastleQueenside(currentRow, currentCol, board)) return true;
        return rowDiff <= 1 && colDiff <= 1;
    }
    @Override
    public List<Move> getAllMoves(int currentRow, int currentCol, ChessPiece[][] board) {
        List<Move> allMoves = new ArrayList<>();

        // Всі можливі напрямки для короля
        int[] rowOffsets = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] colOffsets = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int targetRow = currentRow + rowOffsets[i];
            int targetCol = currentCol + colOffsets[i];

            if (isValidMove(currentRow, currentCol, targetRow, targetCol, board, getColor(), false)) {
                allMoves.add(new Move(currentRow, currentCol, targetRow, targetCol));
            }
        }
       if (canCastleKingside(currentRow, currentCol, board)) {

            allMoves.add(new Move(currentRow, currentCol, currentRow, currentCol + 2));
        }
        if (canCastleQueenside(currentRow, currentCol, board)) {
            allMoves.add(new Move(currentRow, currentCol, currentRow, currentCol - 2));
        }

        return allMoves;
    }

    private boolean canCastleKingside(int currentRow, int currentCol, ChessPiece[][] board) {
        // Додаємо всі умови для рокіровки в сторону короля
        return !hasMoved() && !isCheck() &&
                board[currentRow][currentCol + 1] == null &&
                board[currentRow][currentCol + 2] == null &&
                board[currentRow][currentCol + 3] instanceof Rook &&
                !board[currentRow][currentCol + 3].hasMoved();
    }

    // Метод для перевірки можливості рокіровки короля в сторону ферзя
    private boolean canCastleQueenside(int currentRow, int currentCol, ChessPiece[][] board) {
        // Додаємо всі умови для рокіровки в сторону ферзя
        return !hasMoved() && !isCheck() &&
                board[currentRow][currentCol - 1] == null &&
                board[currentRow][currentCol - 2] == null &&
                board[currentRow][currentCol - 3] == null &&
                board[currentRow][currentCol - 4] instanceof Rook &&
                !board[currentRow][currentCol - 4].hasMoved();
    }
    private boolean isCheck(){return false;}

}
