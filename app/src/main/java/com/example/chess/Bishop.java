package com.example.chess;
import java.util.ArrayList;
import java.util.List;
public class Bishop extends ChessPiece {
    public Bishop(String color) {
        super("Bishop", color, getBishopImageResource(color));
    }

    private static int getBishopImageResource(String color) {
        if (color.equals("White")) {
            return R.drawable.chessblt60;  // Зображення білого слона
        } else {
            return R.drawable.chessbdt60;  // Зображення чорного слона
        }
    }

    // Додайте логіку для правил руху слона, якщо потрібно
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

        // Перевірка чи немає фігур на шляху руху слона по діагоналі
        if (rowDiff == colDiff) {
            int rowStep = Integer.compare(targetRow, currentRow);
            int colStep = Integer.compare(targetCol, currentCol);

            for (int i = 1; i < rowDiff; i++) {
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

        // Можливі ходи по діагоналі
        int[] rowOffsets = {-1, -1, 1, 1};
        int[] colOffsets = {-1, 1, -1, 1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int targetRow = currentRow + rowOffsets[i];
            int targetCol = currentCol + colOffsets[i];

            while (isValidMove(currentRow, currentCol, targetRow, targetCol, board, getColor(), false)) {
                allMoves.add(new Move(currentRow, currentCol, targetRow, targetCol));

                if (board[targetRow][targetCol] != null) {
                    break; // Якщо є фігура на шляху, припиняємо рух
                }

                targetRow += rowOffsets[i];
                targetCol += colOffsets[i];
            }
        }

        return allMoves;
    }
}
