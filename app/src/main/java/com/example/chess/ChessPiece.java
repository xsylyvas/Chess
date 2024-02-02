package com.example.chess;
import java.util.List;import java.util.ArrayList;
public abstract class ChessPiece {
    private boolean hasMoved = false;
    private String name;  // Назва фігури (наприклад, "Pawn", "Rook", "Knight", тощо)
    private String color; // Колір фігури ("White" або "Black")
    private int iconResId; // Ресурс ідентифікатор значка для візуалізації на шахівниці

    public ChessPiece(String name, String color, int iconResId) {
        this.name = name;
        this.color = color;
        this.iconResId = iconResId;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
    public int getIconResId() {
        return iconResId;
    }
    public void markMoved() {
        hasMoved = true;
    }
    public boolean hasMoved()
    {
        return hasMoved;
    }

    public abstract List<Move> getAllMoves(int currentRow, int currentCol, ChessPiece[][] board);
    public abstract boolean isValidMove(int currentRow, int currentCol, int targetRow, int targetCol,ChessPiece[][] board,
                                        String currentPlayer,boolean checkInisCheck);
    // Додайте інші методи та властивості, якщо потрібно
}
