package com.example.chess;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;

public class ConfigureGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_game);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        Button startGameButton = findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 // Отримуємо обрані значення з радіогруп
                RadioGroup levelRadioGroup = findViewById(R.id.levelRadioGroup);
                RadioGroup colorRadioGroup = findViewById(R.id.colorRadioGroup);

                int selectedLevelId = levelRadioGroup.getCheckedRadioButtonId();
                int selectedColorId = colorRadioGroup.getCheckedRadioButtonId();

                // Створюємо новий Intent
                Intent configureGameIntent = new Intent(ConfigureGame.this, StartGameActivity.class);


                String level, color;
                if(selectedLevelId == R.id.easyRadioButton)level = "easy";
                else if(selectedLevelId == R.id.mediumRadioButton)level = "medium";
                else level = "hard";
                if( selectedColorId == R.id.whiteRadioButton)color = "White";
                else color = "Black";

                configureGameIntent.putExtra("selectedLevel", level);
                configureGameIntent.putExtra("selectedColor", color);


                startActivity(configureGameIntent);

            }
        });

    }
}