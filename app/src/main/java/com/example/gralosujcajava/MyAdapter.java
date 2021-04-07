package com.example.gralosujcajava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter {

    private ArrayList<Rank> ranking = new ArrayList<>();
    private RecyclerView recyclerView;

    private class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView login;
        public TextView password;
        public TextView score;

        public MyViewHolder(View view){
            super(view);
            login = (TextView) view.findViewById(R.id.loginTextView);
            password = (TextView) view.findViewById(R.id.passwordTextView);
            score = (TextView) view.findViewById(R.id.scoreTextView);
        }
    }

    public MyAdapter(ArrayList<Rank> ranking, RecyclerView recyclerView){
        this.ranking = ranking;
        this.recyclerView = recyclerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rank, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                int positionToDelete = recyclerView.getChildAdapterPosition(v);
                ranking.remove(positionToDelete);
                notifyItemRemoved(positionToDelete);
                */
            }
        });
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        Rank rank = ranking.get(position);
        ((MyViewHolder)viewHolder).login.setText(rank.getLogin());
        ((MyViewHolder)viewHolder).password.setText(rank.getPassword());
        ((MyViewHolder)viewHolder).score.setText(rank.getScore().toString());
    }

    @Override
    public int getItemCount() {
        return ranking.size();
    }
}
