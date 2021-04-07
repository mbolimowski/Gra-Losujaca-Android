package com.example.gralosujcajava;

import android.content.Intent;

public class Rank {
    private String login;
    private String password;
    private Integer score;

    public Rank(){
    }

    public Rank(String login, String password){
        this.login = login;
        this.password = password;
        this.score = 0;
    }

    public String getLogin(){
        return this.login;
    }

    public String getPassword(){
        return this.password;
    }

    public Integer getScore(){
        return this.score;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setScore(Integer score){
        this.score = score;
    }
}
