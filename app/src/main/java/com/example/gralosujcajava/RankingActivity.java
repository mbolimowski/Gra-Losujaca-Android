package com.example.gralosujcajava;

import android.os.Bundle;

import com.example.gralosujcajava.db.RankDAO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RankDAO rankDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        recyclerView = (RecyclerView)findViewById(R.id.rankingRecyclerView);
        // w celach optymalizacji
        recyclerView.setHasFixedSize(true);
        // ustawiamy LayoutManagera
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // ustawiamy animatora, który odpowiada za animację dodania/usunięcia elementów listy
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        rankDAO = new RankDAO(this);

        loadRanking();
    }

    private void loadRanking(){
        // tworzymy źródło danych - tablicę z rankami
        ArrayList<Rank> ranking = rankDAO.getTop10Ranking();

        // tworzymy adapter oraz łączymy go z RecyclerView
        recyclerView.setAdapter(new MyAdapter(ranking, recyclerView));

    }

    public void back(View view) {
        finish();
    }
}