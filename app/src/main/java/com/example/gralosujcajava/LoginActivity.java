package com.example.gralosujcajava;

import android.content.Intent;
import android.os.Bundle;

import com.example.gralosujcajava.db.DBHelper;
import com.example.gralosujcajava.db.RankDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;
    private Button rankingButton;
    private EditText login;
    private EditText password;
    private RankDAO rankDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerButton = findViewById(R.id.registerButton);
        loginButton = findViewById(R.id.loginButton);
        rankingButton = findViewById(R.id.rankingButton);
        login = findViewById(R.id.loginEditText);
        password = findViewById(R.id.passwordEditText);
        rankDAO = new RankDAO(this);
    }

    public void goToRanking(View view) {
        Intent intent = new Intent(this, RankingActivity.class);
        startActivity(intent);
    }

    public void registerNewPlayer(View view) {
        if(login.getText().toString().length() > 0 && password.getText().toString().length() > 0){
            Rank rank = new Rank(login.getText().toString(), password.getText().toString());

            if(rankDAO.register(rank)){
                Toast.makeText(this, "Rejestracja zakończona powodzeniem!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Rejestracja zakończona niepowodzeniem! Login zajęty...", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(this, "Należy podac login i hasło!", Toast.LENGTH_SHORT).show();
        }
    }

    public void loginPlayer(View view) {
        if(login.getText().toString().length() > 0 && password.getText().toString().length() > 0){

            if(rankDAO.login(login.getText().toString(), password.getText().toString()) == null){
                Toast.makeText(this, "Błędne dane do logowania!", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, "Zalogowano pomyślnie!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("Login", login.getText().toString());
                startActivity(intent);
            }
        }
        else{
            Toast.makeText(this, "Należy podac login i hasło!", Toast.LENGTH_SHORT).show();
        }
    }
}