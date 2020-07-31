package com.neeraj.braintrainer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

   private Button button_1, button_2, button_3, button_4, button_play;
   private TextView mTimer, mQue, mScore, mShowResult;
   private CountDownTimer countDownTimer;
   private String[] multipleAns;
   private int correctAnsPlace;



    float correctAns = 0.0f;
    int totQue = 0, rightAns = 0;

    boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get reference to all members
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_4 = findViewById(R.id.button_4);
        button_play = findViewById(R.id.play);

        mTimer = findViewById(R.id.textView_timer);
        mQue = findViewById(R.id.textView_que);
        mScore = findViewById(R.id.textView_score);
        mShowResult = findViewById(R.id.showResult);
    }

    // play button callback function
    public void play(View view) {
        gameOver = false;
        button_play.setVisibility(View.INVISIBLE);

        // show the board
        button_1.setVisibility(View.VISIBLE);
        button_2.setVisibility(View.VISIBLE);
        button_3.setVisibility(View.VISIBLE);
        button_4.setVisibility(View.VISIBLE);
        mShowResult.setVisibility(View.INVISIBLE);

        String score = "Score 0";
        mScore.setText(score);
        reset();
    }

    // callback for button 1,2,3,4
    public void ans(View view) {
        Button button = (Button) view;
        score(button.getText().toString());
        reset();
    }

    /********************
     * Utility methods*
     *******************/

    private void fill() {
        totQue++;
        Random random = new Random();

        //Contains string  set  to board's button (1, 2, 3, 4)
        multipleAns = new String[4];
        // one random place for correct answer

        //Random Values of operand m and n is decided through rightAns.
        int m = random.nextInt(20) + rightAns;
        int n = random.nextInt(20) + rightAns;

        // Random operator for operand m and n
        int operator = random.nextInt(4);

        String queText;
        switch (operator) {
            case 0:         // Addition '+'
                correctAns = m + n;
                queText = m + "+" + n;
                mQue.setText(queText);
                break;
            case 1:          // multiplication '*'
                correctAns = m * n;
                queText = m + "*" + n;
                mQue.setText(queText);
                break;
            case 2:           // division '/'
                correctAns = (float) m / n;
                queText = m + "/" + n;
                mQue.setText(queText);
                break;
            case 3:          // subtraction '-'
                correctAns = m - n;
                queText = m + "-" + n;
                mQue.setText(queText);
                break;
        }


        correctAnsPlace = random.nextInt(4);

        for (int i = 0; i < 4; i++) {
            if (i == correctAnsPlace) {
                multipleAns[i] = String.valueOf(correctAns)
                        .replaceAll("(\\.0+(?![1-9])|(?<=\\d\\d)\\d+)$", "");
            } else {
                multipleAns[i] = String.valueOf(random.nextInt(15) + (int) correctAns +  correctAns - (int) correctAns)
                        .replaceAll("(\\.0+(?![1-9])|(?<=\\d\\d)\\d+)$", "");
            }
        }

        // fill the board
        button_1.setText(multipleAns[0]);
        button_2.setText(multipleAns[1]);
        button_3.setText(multipleAns[2]);
        button_4.setText(multipleAns[3]);
    }

    private  void reset() {
        if (!gameOver) {
            resetCountDownTimer();
            fill();
            countDownTimer.start();
        }
        else {
            countDownTimer.cancel();
            // show the play button
            button_play.setVisibility(View.VISIBLE);
            // hide the board
            button_1.setVisibility(View.INVISIBLE);
            button_2.setVisibility(View.INVISIBLE);
            button_3.setVisibility(View.INVISIBLE);
            button_4.setVisibility(View.INVISIBLE);

            showFinalResult();
            mShowResult.setVisibility(View.VISIBLE);
            clearAll();

        }
    }

    private void clearAll() {
        rightAns = 0;
        totQue = 0;
    }

    private void  resetCountDownTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(15000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimer.setText(String.valueOf(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                gameOver = true;
                reset();
            }
        };
    }

    private void score(String chosen) {
        if (chosen.equals(multipleAns[correctAnsPlace])) {
            rightAns++;
            String scoreText = "Score "+ rightAns;
            // show current score;
            mScore.setText(scoreText);
        }
        else {
            gameOver = true;
        }
    }

    private void showFinalResult() {
        String result = "score\t\t\t" + rightAns +"\n"
                + "total Que\t" + totQue;
        mShowResult.setText(result);
    }
}