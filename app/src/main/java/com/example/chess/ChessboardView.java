package com.example.chess;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class ChessboardView extends View {
    private Chessboard chessboard;  // Змінено поле board на клас Chessboard
    private Paint darkSquarePaint;
    private Paint lightSquarePaint;
    private Paint piecePaint;

    private int selectedRow = -1;
    private int selectedCol = -1;
    private String currentPlayer = "White";
    private String playerColor;  // Колір гравця
    private String aiColor;
    private Move moveAiPlayer= new Move (-1,-1,-1,-1);

    public ChessboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer.equals("White")) ? "Black" : "White";
    }

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }

    public void setAiColor(String aiColor) {
        this.aiColor = aiColor;
    }

    public void setChessboard(Chessboard chessboard) {
        this.chessboard = chessboard;
        invalidate(); // Запускає перерисовку для відображення нового стану дошки
    }



    private void init() {
        darkSquarePaint = new Paint();

        darkSquarePaint.setColor(Color.argb(180,190,110,16)); // Темно-коричневий колір

        lightSquarePaint = new Paint();
        lightSquarePaint.setColor(Color.argb(180,242, 232, 174)); // Бежевий колір

        piecePaint = new Paint();
        piecePaint.setColor(Color.parseColor("#F7C148"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int squareSize = getWidth() / 8;

        // Draw the chessboard
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    canvas.drawRect(i * squareSize, j * squareSize,
                            (i + 1) * squareSize, (j + 1) * squareSize, lightSquarePaint);
                } else {
                    canvas.drawRect(i * squareSize, j * squareSize,
                            (i + 1) * squareSize, (j + 1) * squareSize, darkSquarePaint);
                }
            }
        }

        if (selectedRow != -1 && selectedCol != -1) {
            canvas.drawRect(selectedCol * squareSize, selectedRow * squareSize,
                    (selectedCol + 1) * squareSize, (selectedRow + 1) * squareSize, piecePaint);
        }


        // Draw chess pieces
        if (chessboard != null) {
            ChessPiece[][] board = chessboard.getBoard();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    ChessPiece piece = board[i][j];
                    if (piece != null) {
                        float x = j * squareSize;
                        float y = i * squareSize;

                        // Draw piece icon
                        drawPieceIcon(canvas, piece.getIconResId(), x, y, squareSize);
                    }
                }
            }
        }

        // Highlight the selected piece or cell
    }

    private void drawPieceIcon(Canvas canvas, int iconResId, float x, float y, int size) {
        // Assuming that the piece icon is a drawable resource
        BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(iconResId);
        Bitmap bitmap = Bitmap.createScaledBitmap(bitmapDrawable.getBitmap(), size, size, false);
        canvas.drawBitmap(bitmap, x, y, piecePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                handleTouchDown(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                handleTouchUp(event.getX(), event.getY());
                break;
        }

        return true;
    }

    private void handleTouchDown(float x, float y) {
        int squareSize = getWidth() / 8;
        int col = (int) (x / squareSize);
        int row = (int) (y / squareSize);

        if (row >= 0 && row < 8 && col >= 0 && col < 8) {
            selectedRow = row;
            selectedCol = col;
            invalidate(); // Запускає перерисовку для відображення виділеної клітинки
        }
    }

    @SuppressLint("SuspiciousIndentation")
    private void handleTouchUp(float x, float y) {
        // Перевірте, чи є обрані координати в межах дошки та чи є обрана фігура
        if (selectedRow != -1 && selectedCol != -1 && chessboard.getPiece(selectedRow, selectedCol) != null) {
            int squareSize = getWidth() / 8;

            // Отримайте нові координати під час відпускання торкання
            int releasedCol = (int) (x / squareSize);
            int releasedRow = (int) (y / squareSize);

            ChessPiece selectedPiece = chessboard.getPiece(selectedRow, selectedCol);





            // Перевірте, чи фігура може здійснити такий хід (логіка перевірки ходу має бути в класі ChessPiece)
            if ( selectedPiece.isValidMove(selectedRow, selectedCol, releasedRow, releasedCol, chessboard.getBoard(),currentPlayer,false))
            {
                if(selectedPiece.getColor() != currentPlayer)return;

                //new EndGame().Win(getContext(),currentPlayer);


                   selectedPiece.markMoved();

                    chessboard.movePiece(selectedRow, selectedCol, releasedRow, releasedCol);
                    switchPlayer();
                   invalidate();
                    new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AIPlayer a = new AIPlayer("Black");
                        Move moveAi = a.chooseBestMove(chessboard, currentPlayer);
                        chessboard.getPiece(moveAi.getFromRow(), moveAi.getFromCol()).markMoved();

                        chessboard.movePiece(moveAi.getFromRow(), moveAi.getFromCol(), moveAi.getToRow(), moveAi.getToCol());
                        switchPlayer();
                        selectedCol = moveAi.getFromCol();
                        selectedRow = moveAi.getFromRow();
                        // Візуалізація ходу ШІ
                        invalidate();
                    }
                }, 1000);
                       // ((Activity) getContext()).finish();

            }


        }

        // Скинути обрані координати
        selectedRow = -1;
        selectedCol = -1;

    }
}
