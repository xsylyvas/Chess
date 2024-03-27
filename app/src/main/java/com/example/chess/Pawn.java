package com.example.chess;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
public class Pawn extends ChessPiece {



    public Pawn(String color) {
        super("Pawn", color, getPawnImageResource(color));
    }
    private String playerColor;
    private static int getPawnImageResource(String color) {
        if (color.equals("White")) {
            return R.drawable.chessplt60;  // Зображення білого пішака
        } else {
            return R.drawable.chesspdt60;  // Зображення чорного пішака
        }
    }


    public void setPlayerColor(String color)
    {
        playerColor = color;
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

        int forwardDirection ;

        if(Objects.equals(currentPlayer, playerColor))forwardDirection = -1;
        else forwardDirection = 1;


        // Рух пішака дійсний, якщо він переміщується на одну клітинку вперед
        boolean isValidForwardMove = targetCol == currentCol && targetRow == currentRow + forwardDirection &&
                board[targetRow][targetCol] == null;

        // Рух пішака дійсний, якщо він переміщується на дві клітинки вперед при першому ході

        boolean isValidDoubleForwardMove = !hasMoved() && targetCol == currentCol && targetRow == currentRow + 2 * forwardDirection &&
                board[targetRow][targetCol] == null && board[targetRow - forwardDirection][targetCol] == null;

        // Рух пішака дійсний, якщо він атакує по діагоналі
        boolean isValidDiagonalMove = Math.abs(targetCol - currentCol) == 1 && targetRow == currentRow + forwardDirection &&
                board[targetRow][targetCol] != null && !board[targetRow][targetCol].getColor().equals(getColor());

        return isValidForwardMove || isValidDoubleForwardMove || isValidDiagonalMove;
    }


    @Override
    public List<Move> getAllMoves(int currentRow, int currentCol, ChessPiece[][] board) {
        List<Move> allMoves = new ArrayList<>();
        int forwardDirection=-1;
        if(Objects.equals(getColor(), playerColor))forwardDirection = -1;
        else forwardDirection = 1;

        // Перевіряємо можливість руху на одну клітинку вперед
        int targetRow = currentRow + forwardDirection;
        int targetCol = currentCol;
        if (isValidMove(currentRow, currentCol, targetRow, targetCol, board, getColor(), false)) {
            allMoves.add(new Move(currentRow, currentCol, targetRow, targetCol));
        }

        // Перевіряємо можливість руху на дві клітинки вперед (при першому ході)
        targetRow = currentRow + 2 * forwardDirection;
        targetCol = currentCol;
        if (!hasMoved() && isValidMove(currentRow, currentCol, targetRow, targetCol, board, getColor(), false)) {
            allMoves.add(new Move(currentRow, currentCol, targetRow, targetCol));
        }

        // Перевіряємо можливість атаки по діагоналі
        targetCol = currentCol - 1;
        if (isValidMove(currentRow, currentCol, currentRow + forwardDirection, targetCol, board, getColor(), false)) {
            allMoves.add(new Move(currentRow, currentCol, currentRow + forwardDirection, targetCol));
        }

        targetCol = currentCol + 1;
        if (isValidMove(currentRow, currentCol, currentRow + forwardDirection, targetCol, board, getColor(), false)) {
            allMoves.add(new Move(currentRow, currentCol, currentRow + forwardDirection, targetCol));
        }

        return allMoves;
    }
}
