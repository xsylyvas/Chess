package com.example.chess;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button exitButton = findViewById(R.id.exitButton);
        Button startGameButton = findViewById(R.id.startGameButton);

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent configureGameIntent = new Intent(MainActivity.this, ConfigureGame.class);
                startActivity(configureGameIntent);
            }
        });
    }
}