package com.example.gralosujcajava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gralosujcajava.db.DBHelper;
import com.example.gralosujcajava.db.RankDAO;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button answerButton;
    private Button newGameButton;
    private Button rankingButton;
    private EditText inputText;
    private TextView counterView;
    private TextView pointsView;
    private TextView messageView;
    private DBHelper dbHelper = new DBHelper(this);
    private int randomNumber = 0;
    private int numberOfShots = 0;
    private int score = 0;
    private RankDAO rankDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String login = getIntent().getStringExtra("Login").toString();

        answerButton = findViewById(R.id.answerButton);
        newGameButton = findViewById(R.id.newGameButton);
        rankingButton = findViewById(R.id.rankingButton);
        inputText = findViewById(R.id.editText);
        counterView = findViewById(R.id.textCounter);
        pointsView = findViewById(R.id.textPoint);
        messageView = findViewById(R.id.textHint);
        rankDAO = new RankDAO(this);

        score = rankDAO.getScore(login);
        pointsView.setText("Zdobyte punkty: " + score);
        gameStart(messageView);

        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(inputText.length() > 0){
                    int number = Integer.parseInt(inputText.getText().toString());
                    answer(number, counterView, pointsView, messageView, login);
                    inputText.setText("");
                }
                else{
                    dialogShow("Błąd!!!", "Nie poprawna liczba...");
                }
            }
        });


        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gameStart(messageView);
                counterView.setText("Ilość prób: 0");
            }
        });

        rankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RankingActivity.class);
                startActivity(intent);
            }
        });
    }

    private void gameStart(TextView messageView){
        numberOfShots = 0;
        randomNumber = new Random().nextInt(20);
        messageView.setText("PodajLiczbę!");
        Toast.makeText(this, "Zaczęto nową grę!", Toast.LENGTH_SHORT).show();
    }

    private void answer(int number, TextView counterView, TextView pointsView, TextView messageView, String login){
        if(number < 0 || number > 20){
            dialogShow("ERROR", "Liczba musi być z zakresu od 0 do 20!!!");
        }
        else{
            if(number == randomNumber){
                numberOfShots++;
                pointsCalculator();
                pointsView.setText("Zdobyte punkty: " + score);
                rankDAO.updateScore(login, score);
                gameStart(messageView);
            }
            else{
                numberOfShots++;
                if(number > randomNumber){
                    messageView.setText("MNIEJ BYKU!!!");
                }
                else{
                    messageView.setText("WIĘCEJ BYKU!!!");
                }
                if(numberOfShots == 10){
                    dialogShow("Przegranko", "Jesteć cienias...");
                    gameStart(messageView);
                }
            }
            counterView.setText("Ilość prób: " + numberOfShots);
        }
    }
    private void dialogShow(String title, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int id){
                //finish(); błędne... kończyło MainActivity
            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    private void pointsCalculator(){
        int points = 5;
        switch (numberOfShots){
            case 1:
                points = 5;
                break;
            case 2:
            case 3:
            case 4:
                points = 3;
                break;
            case 5:
            case 6:
                points = 2;
                break;
            case 7:
            case 8:
            case 9:
            case 10:
                points = 1;
                break;
            default:
                break;
        }
        this.score += points;
        dialogShow("Brawo! Udało się trafić :)", "Zdobywasz" + points + "punktów!");
    }
}