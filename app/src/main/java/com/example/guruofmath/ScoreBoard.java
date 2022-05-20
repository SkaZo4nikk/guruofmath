package com.example.guruofmath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ScoreBoard extends AppCompatActivity {

    private Button mStartAgain;
    private TextView mCurrentScore, mBestScore;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        Bundle Score = getIntent().getExtras();
        int cScore = Integer.parseInt(Score.get("CurrentScore").toString());

        mBestScore = findViewById(R.id.BestScore);
        mCurrentScore = findViewById(R.id.CurrentScore);
        mCurrentScore.setText("Набрано очков: " + cScore);

        users = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        users.child("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String highScore = snapshot.getValue().toString();
                mBestScore.setText("Лучший результат: " + highScore);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mStartAgain = findViewById(R.id.StartAgain);

        mStartAgain.setOnClickListener(v -> {
            startActivity(new Intent(ScoreBoard.this, MathTest.class));
            finish();
        });


    }
}