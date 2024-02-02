package com.example.chess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

public class EndGame {
    public void Draw(Context context) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        // Створіть TextView для налаштування вирівнювання тексту та розміру
        TextView messageText = new TextView(context);
        messageText.setText("Stalemate");
        messageText.setGravity(Gravity.CENTER); // Вирівняти текст по центру
        messageText.setTextSize(45); // Встановити розмір тексту
        messageText.setTextColor(Color.rgb(76, 48, 115));
        builder.setView(messageText);

        builder.setPositiveButton("Back to menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }
        });

        builder.show();
    }
    public void Win(Context context, String player) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);


        // Створіть TextView для налаштування вирівнювання тексту та розміру
        TextView messageText = new TextView(context);
        messageText.setText(player+" won!");
        messageText.setGravity(Gravity.CENTER); // Вирівняти текст по центру
        messageText.setTextSize(45); // Встановити розмір тексту
        messageText.setTextColor(Color.rgb(76, 48, 115));
        builder.setView(messageText);

        builder.setPositiveButton("Back to menu", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ((Activity) context).finish();
            }
        });

        builder.show();
    }
}
