package com.example.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Chessboard {


    private static final int SIZE = 8;

    private ChessPiece[][] board;


    public Chessboard() {

        this.board = new ChessPiece[8][8];


    }

    public Chessboard(Chessboard original) {
        this.board = new ChessPiece[8][8];


        for (int i = 0; i < 8; i++) {
            System.arraycopy(original.board[i], 0, this.board[i], 0, 8);
        }
    }

    public Chessboard(ChessPiece[][] initialBoard) {

        this.board = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = initialBoard[i][j];

                if (piece instanceof Pawn) {
                    this.board[i][j] = new Pawn(piece.getColor());
                } else if (piece instanceof Rook) {
                    this.board[i][j] = new Rook(piece.getColor());
                } else if (piece instanceof Knight) {
                    this.board[i][j] = new Knight(piece.getColor());
                } else if (piece instanceof Bishop) {
                    this.board[i][j] = new Bishop(piece.getColor());
                } else if (piece instanceof Queen) {
                    this.board[i][j] = new Queen(piece.getColor());
                } else if (piece instanceof King) {
                    this.board[i][j] = new King(piece.getColor());
                } else {
                    this.board[i][j] = null;
                }
                if(this.board[i][j] != null && piece.hasMoved())this.board[i][j].markMoved();
            }
        }


    }

    public ChessPiece[][] getBoard() {
        return board;
    }


    public void initializeBoard(String color) {

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(color.equals("White") ? "Black" : "White");
            board[6][i] = new Pawn(color);
            ((Pawn) board[1][i]).setPlayerColor(color);
            ((Pawn) board[6][i]).setPlayerColor(color);
        }

        // Розміщуємо інші фігури
        // Додаємо тури
        board[0][0] = new Rook(color.equals("White") ? "Black" : "White");
        board[0][7] = new Rook(color.equals("White") ? "Black" : "White");
        board[7][0] = new Rook(color);
        board[7][7] = new Rook(color);

        // Додаємо коні
        board[0][1] = new Knight(color.equals("White") ? "Black" : "White");
        board[0][6] = new Knight(color.equals("White") ? "Black" : "White");
        board[7][1] = new Knight(color);
        board[7][6] = new Knight(color);

        // Додаємо слони
        board[0][2] = new Bishop(color.equals("White") ? "Black" : "White");
        board[0][5] = new Bishop(color.equals("White") ? "Black" : "White");
        board[7][2] = new Bishop(color);
        board[7][5] = new Bishop(color);

        // Додаємо ферзей
        board[0][3] = new Queen(color.equals("White") ? "Black" : "White");
        board[7][3] = new Queen(color);

        // Додаємо королів
        board[0][4] = new King(color.equals("White") ? "Black" : "White");
        board[7][4] = new King(color);
    }

    public ChessPiece getPiece(int row, int col) {
        return board[row][col];
    }

    public boolean isCheck(String playerColor) {

        String enemyColor;
        if(Objects.equals(playerColor, "White"))enemyColor = "Black";
        else enemyColor = "White";
        int kingRow = -1;
        int kingCol = -1;


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


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && !piece.getColor().equals(playerColor)) {

                    if (piece.isValidMove(i, j, kingRow, kingCol, board, enemyColor, true)) {

                        return true; // Шах

                    }
                }
            }
        }

        return false; // Немає шаху
    }
    public boolean isCheckmate(String currentPlayer) {

        List<Move> allMoves = getAllMoves(currentPlayer);


        for (Move move : allMoves) {
            Chessboard tempBoard = new Chessboard(getBoard());
            tempBoard.movePiece(move.getFromRow(), move.getFromCol(), move.getToRow(), move.getToCol());


            if (!tempBoard.isCheck(currentPlayer)) {
                return false;
            }
        }


        return true;
    }
    public boolean isDraw(String currentPlayer) {
        return isStalemate(currentPlayer) || isInsufficientMaterial();
    }
    public boolean isStalemate(String currentPlayer) {

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
