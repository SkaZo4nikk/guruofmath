package com.example.guruofmath;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.guruofmath.Models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ScoreBoard extends AppCompatActivity {

    private Button mStartAgain;
    private TextView mCurrentScore, mBestScore, mFirstPlace, mSecondPlace, mThirdPlace;
    private FirebaseDatabase db;
    private DatabaseReference users;
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private List<Map<String, String>> list = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board);

        Bundle Score = getIntent().getExtras();
        int cScore = Integer.parseInt(Score.get("CurrentScore").toString());

        mBestScore = findViewById(R.id.BestScore);
        mCurrentScore = findViewById(R.id.CurrentScore);
        mFirstPlace = findViewById(R.id.FirstPlace);
        mSecondPlace = findViewById(R.id.SecondPlace);
        mThirdPlace = findViewById(R.id.ThirdPlace);

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

        DatabaseReference yourRef = FirebaseDatabase.getInstance().getReference("Users");
        Query query = yourRef.orderByChild("score").limitToLast(3);
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //System.out.println(snapshot.child("score").getValue());
                //System.out.println(snapshot.child("name").getValue());
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("name", snapshot.child("name").getValue().toString());
                hashMap.put("score", snapshot.child("score").getValue().toString());
                list.add(hashMap);
                if(list.size() == 3){
                    UpdateView();
                }
                System.out.println(list);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //System.out.println(snapshot.getValue());
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                //System.out.println(snapshot.getValue());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                //System.out.println(snapshot.getValue());
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

    public void UpdateView(){
        mFirstPlace.setText("Первое место: " + list.get(2).get("name") + " " + list.get(2).get("score"));
        mSecondPlace.setText("Второе место: " + list.get(1).get("name")+ " " + list.get(1).get("score"));
        mThirdPlace.setText("Третье место: " + list.get(0).get("name")+ " " + list.get(0).get("score"));
    }
}