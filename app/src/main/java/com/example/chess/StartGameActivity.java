package com.example.chess;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.appbar.AppBarLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;



public class StartGameActivity extends AppCompatActivity {
    private ChessboardView chessboardView;
    private Chessboard chessboard;
    private Player player;
    private AIPlayer aiPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Повертаємося на попередню активність при натисканні на стрілку
            }
        });

        player = new Player("White","White");
        aiPlayer = new AIPlayer("Black");

        chessboardView = findViewById(R.id.chessBoardView);
        chessboard = new Chessboard();
        chessboard.initializeBoard();
        chessboardView.setAiColor(aiPlayer.getColor());
        chessboardView.setPlayerColor(player.getColor());
        chessboardView.setChessboard(chessboard);



    }
}
