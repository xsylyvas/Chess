package com.example.chess;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.util.Objects;


public class StartGameActivity extends AppCompatActivity {
    private ChessboardView chessboardView;
    private Chessboard chessboard;

    String level, color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        int depth = 0;
        if (extras != null) {
           level = extras.getString("selectedLevel");
           color = extras.getString("selectedColor");
          
            switch(level) {
                case "easy":
                    depth = 1;
                    break;
                case "medium":
                    depth = 2;
                    break;
                case "hard":
                    depth = 3;
                    break;
                default:
                    depth = 0; 
                    break;
            }
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Повертаємося на попередню активність при натисканні на стрілку
            }
        });


       
        chessboardView = findViewById(R.id.chessBoardView);
        chessboard = new Chessboard();
        chessboard.initializeBoard(color);
        chessboardView.setPlayer(color);
        chessboardView.setAIPlayer(color.equals("White") ? "Black" : "White",depth);
        chessboardView.setCurrentPlayer(color);
        chessboardView.setChessboard(chessboard);

        if(Objects.equals(color, "Black")) { // make a pre-move A
            Move move = AIPlayer.chooseBestMove(chessboardView.getChessboard(),"White");
            chessboardView.getChessboard().movePiece(move.getFromRow(),move.getFromCol(),move.getToRow(),move.getToCol());
            chessboardView.setCurrentPlayer("Black");
        }



    }
}
