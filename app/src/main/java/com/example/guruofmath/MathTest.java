package com.example.guruofmath;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MathTest extends AppCompatActivity {

    private TextView mSolveExample, mScorePoints, mHealthPoint, mTimeOfQ;
    int mScore;
    private int mLife = 3;
    private Button FirstButton, SecondButton, ThirdButton, FourthButton;
    private int ans;
    private FirebaseDatabase db; //подключение к db
    private DatabaseReference users; //работа с табличками dp
    private String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    RelativeLayout layout;
    private int imageID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_test);

        layout = findViewById(R.id.image);
        mTimeOfQ = findViewById(R.id.TimeOfQ);
        mSolveExample = findViewById(R.id.SolveExample);
        mScorePoints = findViewById(R.id.ScorePoints);
        mHealthPoint = findViewById(R.id.HealthPoint);
        mScorePoints.setText("Текущий счет: " + mScore);
        mHealthPoint.setText("Жизни: " + mLife);

        FirstButton = findViewById(R.id.FirstAnswer);
        SecondButton = findViewById(R.id.SecondAnswer);
        ThirdButton = findViewById(R.id.ThirdAnswer);
        FourthButton = findViewById(R.id.FourthAnswer);

        db = FirebaseDatabase.getInstance(); //подключение к dp
        users = db.getReference("Users"); //работа с табличкой Users

        timer.start();

        MathQuestion();
        CreateButtons();

        FirstButton.setOnClickListener(v->{
            imageID=getResources().getIdentifier("com.example.guruofmath:drawable/god2",null,null);
            layout.setBackgroundResource(imageID);
            timer.cancel();
            timer.start();
            CheckAns(FirstButton.getText().toString());
            MathQuestion();
            CreateButtons();
        });

        SecondButton.setOnClickListener(v->{
            timer.cancel();
            timer.start();
            CheckAns(SecondButton.getText().toString());
            MathQuestion();
            CreateButtons();
        });

        ThirdButton.setOnClickListener(v->{
            timer.cancel();
            timer.start();
            CheckAns(ThirdButton.getText().toString());
            MathQuestion();
            CreateButtons();
        });

        FourthButton.setOnClickListener(v->{
            timer.cancel();
            timer.start();
            CheckAns(FourthButton.getText().toString());
            MathQuestion();
            CreateButtons();
        });
    }

    public void MathQuestion(){
        Random rd = new Random();
        int a = rd.nextInt(30) + 1;
        int b = rd.nextInt(30) + 1;
        ans = 0;
        String oper = "";
        switch (rd.nextInt(3)) {
            case 0:
                oper = "+";
                ans = a + b;
                break;
            case 1:
                oper = "*";
                ans = a * b;
                break;
            case 2:
                oper = "-";
                ans = a - b;
                break;
        }
        mSolveExample.setText(a + " " + oper + " " + b);
    }

    private void CheckAns(String userAns){

        if(userAns.equals(String.valueOf(ans))){
            mScore++;
            mScorePoints.setText("Текущий счет: " + mScore);
        } else {
            if(mLife > 0){
                mLife--;
                mHealthPoint.setText("Жизни: " + mLife);
            } else{
                users = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                timer.cancel();

                users.child("score").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String highScore = snapshot.getValue().toString();
                        System.out.println(highScore);
                        if(mScore > Integer.parseInt(highScore)){
                            users.child("score").setValue(String.valueOf(mScore));
                            System.out.println("Рекорд побит");
                        } else{
                            System.out.println("Рекорд не побит");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                Intent intent = new Intent(MathTest.this, ScoreBoard.class);
                intent.putExtra("CurrentScore", mScore);
                startActivity(intent);
                finish();
            }
        }
    }

    private void CreateButtons(){
        Random rd = new Random();
        List<Integer> list = Arrays.asList(ans + (rd.nextInt(10) + 1), ans + (rd.nextInt(10) + 1), ans + (rd.nextInt(10) + 1), ans);
        Collections.shuffle(list); //перемешиваем массив
        FirstButton.setText(String.valueOf(list.get(0)));
        SecondButton.setText(String.valueOf(list.get(1)));
        ThirdButton.setText(String.valueOf(list.get(2)));
        FourthButton.setText(String.valueOf(list.get(3)));
    }

    CountDownTimer timer = new CountDownTimer(5000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTimeOfQ.setText("Время: " + (millisUntilFinished / 1000 + 1));
        }

        @Override
        public void onFinish() {
            mLife = 0;
            CheckAns("");
        }
    };

}
