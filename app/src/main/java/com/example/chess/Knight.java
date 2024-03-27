package com.example.chess;
import java.util.ArrayList;
import java.util.List;
public class Knight extends ChessPiece {
    public Knight(String color) {
        super("Knight", color, getKnightImageResource(color));
    }

    private static int getKnightImageResource(String color) {
        if (color.equals("White")) {
            return R.drawable.chessnlt60;
        } else {
            return R.drawable.chessndt60;
        }
    }


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

        int rowDiff = Math.abs(targetRow - currentRow);
        int colDiff = Math.abs(targetCol - currentCol);

        // Рух коня має бути L-подібним
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }
    @Override
    public List<Move> getAllMoves(int currentRow, int currentCol, ChessPiece[][] board) {
        List<Move> allMoves = new ArrayList<>();

        // Можливі ходи коня
        int[] rowOffsets = {-2, -1, 1, 2, 2, 1, -1, -2};
        int[] colOffsets = {1, 2, 2, 1, -1, -2, -2, -1};

        for (int i = 0; i < rowOffsets.length; i++) {
            int targetRow = currentRow + rowOffsets[i];
            int targetCol = currentCol + colOffsets[i];

            if (isValidMove(currentRow, currentCol, targetRow, targetCol, board, getColor(), false)) {
                allMoves.add(new Move(currentRow, currentCol, targetRow, targetCol));
            }
        }

        return allMoves;
    }
}
