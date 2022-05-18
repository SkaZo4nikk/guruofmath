package com.example.guruofmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class ScoreBoard extends AppCompatActivity {

    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        button2 = findViewById(R.id.button2);

        button2.setOnClickListener(v -> {
            startActivity(new Intent(ScoreBoard.this, MathTest.class));
            finish();
        });


    }
}