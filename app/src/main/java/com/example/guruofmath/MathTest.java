package com.example.guruofmath;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MathTest extends AppCompatActivity {

    private TextView mSolveExample, mScorePoints, mHealthPoint;
    private int mCount;
    private int mLife = 3;
    private Button FirstButton, SecondButton, ThirdButton, FourthButton;
    private int ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_test);

        mSolveExample = findViewById(R.id.SolveExample);
        mScorePoints = findViewById(R.id.ScorePoints);
        mHealthPoint = findViewById(R.id.HealthPoint);
        mScorePoints.setText("Текущий счет: " + mCount);
        mHealthPoint.setText("Жизни: " + mLife);

        FirstButton = findViewById(R.id.FirstAnswer);
        SecondButton = findViewById(R.id.SecondAnswer);
        ThirdButton = findViewById(R.id.ThirdAnswer);
        FourthButton = findViewById(R.id.FourthAnswer);

        MathQuestion();
        CreateButtons();

        FirstButton.setOnClickListener(v->{
            CheckAns(FirstButton.getText().toString());
            MathQuestion();
            CreateButtons();
        });

        SecondButton.setOnClickListener(v->{
            CheckAns(SecondButton.getText().toString());
            MathQuestion();
            CreateButtons();
        });

        ThirdButton.setOnClickListener(v->{
            CheckAns(ThirdButton.getText().toString());
            MathQuestion();
            CreateButtons();
        });

        FourthButton.setOnClickListener(v->{
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
        char oper = '0';
        switch (rd.nextInt(3)) {
            case 0:
                oper = '+';
                ans = a + b;
                break;
            case 1:
                oper = '*';
                ans = a * b;
                break;
            case 2:
                oper = '-';
                ans = a - b;
                break;
        }
        mSolveExample.setText(a + " " + oper + " " + b);
    }

    private void CheckAns(String userAns){
        if(userAns.equals(String.valueOf(ans))){
            mCount++;
            mScorePoints.setText("Текущий счет: " + mCount);
        } else {
            if(mLife > 0){
                mLife--;
                mHealthPoint.setText("Жизни: " + mLife);
            } else{
                Intent intent = new Intent(MathTest.this, ScoreBoard.class);
                startActivity(intent);
            }
        }
    }

    private void CreateButtons(){
        Random rd = new Random();
        List<Integer> list = Arrays.asList(ans + (rd.nextInt(20) + 1), ans + (rd.nextInt(20) + 1), ans + (rd.nextInt(20) + 1), ans);
        Collections.shuffle(list); //перемешиваем массив
        FirstButton.setText(String.valueOf(list.get(0)));
        SecondButton.setText(String.valueOf(list.get(1)));
        ThirdButton.setText(String.valueOf(list.get(2)));
        FourthButton.setText(String.valueOf(list.get(3)));
    }
}