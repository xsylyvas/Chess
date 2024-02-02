package com.example.chess;

import java.util.ArrayList;
import java.util.List;

public class Chessboard {


    private static final int SIZE = 8;

    private ChessPiece[][] board;


    public Chessboard() {

        this.board = new ChessPiece[8][8];
        // Додатковий код ініціалізації залишається незмінним
        initializeBoard();
    }

    public Chessboard(Chessboard original) {
        this.board = new ChessPiece[8][8];

        // Копіюємо фігури з оригінальної дошки в новий екземпляр
        for (int i = 0; i < 8; i++) {
            System.arraycopy(original.board[i], 0, this.board[i], 0, 8);
        }
    }

    public Chessboard(ChessPiece[][] initialBoard) {

        this.board = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(initialBoard[i], 0, this.board[i], 0, 8);
        }
    }

    public ChessPiece[][] getBoard() {
        return board;
    }


    public void initializeBoard() {
        // Ініціалізація фігур на стандартних початкових позиціях
        // Додаємо фігури для прикладу

        // Додаємо пішаки
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("Black");
            board[6][i] = new Pawn("White");
        }

        // Додаємо тури
        board[0][0] = new Rook("Black");
        board[0][7] = new Rook("Black");
        board[7][0] = new Rook("White");
        board[7][7] = new Rook("White");

        // Додаємо коні
        board[0][1] = new Knight("Black");
        board[0][6] = new Knight("Black");
        board[7][1] = new Knight("White");
        board[7][6] = new Knight("White");

        // Додаємо слони
        board[0][2] = new Bishop("Black");
        board[0][5] = new Bishop("Black");
        board[7][2] = new Bishop("White");
        board[7][5] = new Bishop("White");

        // Додаємо ферзей
        board[0][3] = new Queen("Black");
        board[7][3] = new Queen("White");

        // Додаємо королів
        board[0][4] = new King("Black");
        board[7][4] = new King("White");
    }
    public ChessPiece getPiece(int row, int col) {
        return board[row][col];
    }

    public boolean isCheck(String playerColor) {
        int kingRow = -1;
        int kingCol = -1;

        // Знаходимо позицію короля поточного гравця
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece instanceof King && piece.getColor().equals(playerColor)) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
        }

        // Перевіряємо, чи король знаходиться під атакою
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(playerColor)) {
                    // Перевіряємо, чи може фігура атакувати короля
                    if (piece.isValidMove(i, j, kingRow, kingCol, board, playerColor, true)) {

                        return true; // Шах

                    }
                }
            }
        }

        return false; // Немає шаху
    }
    public boolean isCheckmate(String currentPlayer) {
        // Отримати всі можливі ходи для гравця
        List<Move> allMoves = getAllMoves(currentPlayer);

        // Перевірити, чи для кожного ходу гравця відсутня можливість врятуватися від шаху
        for (Move move : allMoves) {
            Chessboard tempBoard = new Chessboard(getBoard());
            tempBoard.movePiece(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());

            // Якщо гравець не в шаху після ходу, то ситуація не матова
            if (!tempBoard.isCheck(currentPlayer)) {
                return false;
            }
        }

        // Якщо для жодного з можливих ходів гравця немає можливості врятуватися, то мат
        return true;
    }
    public boolean isDraw(String currentPlayer) {
        return isStalemate(currentPlayer) || isInsufficientMaterial();
    }
    public boolean isStalemate(String currentPlayer) {
        // Перевіряємо, чи є сталемейт для поточного гравця
        List<Move> availableMoves = getAllMoves(currentPlayer);

        if (availableMoves.isEmpty() && !isCheck(currentPlayer))return true;
        return false;
    }
    public boolean isInsufficientMaterial()
    {
        int  otherCount = 0;
        int whiteBishopCount = 0, whiteKnightCount = 0;
        int blackBishopCount = 0, blackKnightCount = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = getPiece(i, j);
                if (piece != null) {
                    if (piece.getColor().equals("White")) {
                        if (piece.getName().equals("Bishop")) {
                            whiteBishopCount++;
                        } else if (piece.getName().equals("Knight")) {
                            whiteKnightCount++;
                        } else {
                            otherCount++;
                        }
                    } else {
                        if (piece.getName().equals("Bishop")) {
                            blackBishopCount++;
                        } else if (piece.getName().equals("Knight")) {
                            blackKnightCount++;
                        } else {
                            otherCount++;
                        }
                    }
                }
            }
        }
        if(otherCount > 2)return false;
        if ((whiteBishopCount + whiteKnightCount == 0) && (blackBishopCount + blackKnightCount == 0)) {
            return true;
        }

        // Умова для короля та слона проти короля
        if ((whiteBishopCount == 1 && whiteKnightCount == 0) && (blackBishopCount == 0 && blackKnightCount == 0)) {
            return true;
        }
        if ((whiteBishopCount == 0 && whiteKnightCount == 0) && (blackBishopCount == 1 && blackKnightCount == 0)) {
            return true;
        }

        // Умова для короля та кінь проти короля
        if ((whiteBishopCount == 0 && whiteKnightCount == 1) && (blackBishopCount == 0 && blackKnightCount == 0)) {
            return true;
        }
        if ((whiteBishopCount == 0 && whiteKnightCount == 0) && (blackBishopCount == 0 && blackKnightCount == 1)) {
            return true;
        }

        return false;
    }



    public void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = null;
        if((toRow == 0 || toRow == 7) && board[toRow][toCol].getName() == "Pawn")
        {
            board[toRow][toCol] = new Queen(board[toRow][toCol].getColor());
        }
        if ( board[toRow][toCol].getName()=="King" && fromCol+2==toCol )
        {
            board[fromRow][fromCol+1] = board[toRow][fromCol+3];
            board[toRow][fromCol+3] = null;
        }
        if ( board[toRow][toCol].getName()=="King" && fromCol-2==toCol )
        {
            board[fromRow][fromCol-1] = board[toRow][fromCol-4];
            board[toRow][fromCol-4] = null;
        }
    }

    public List<Move> getAllMoves(String playerColor) {
        List<Move> allMoves = new ArrayList<>();

        // Проходимо по всіх клітинках дошки
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = board[row][col];

                // Перевіряємо, чи на клітинці є фігура і чи вона належить поточному гравцеві
                if (piece != null && piece.getColor().equals(playerColor)) {
                    // Отримуємо всі можливі ходи для цієї фігури
                    List<Move> pieceMoves = piece.getAllMoves(row, col, board);

                    // Додаємо усі можливі ходи цієї фігури в загальний список
                    allMoves.addAll(pieceMoves);
                }
            }
        }

        return allMoves;
    }
}
