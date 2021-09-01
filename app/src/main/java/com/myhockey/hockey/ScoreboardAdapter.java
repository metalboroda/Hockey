package com.myhockey.hockey;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ScoreboardAdapter extends RecyclerView.Adapter<ScoreboardAdapter.ExampleViewHolder> {
    private ArrayList<ScoreboardItem> mExampleList;

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView nameText;
        public TextView scoreText;

        public ExampleViewHolder(View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.rulesText);
            scoreText = itemView.findViewById(R.id.scoreText);
        }
    }

    public ScoreboardAdapter(ArrayList<ScoreboardItem> exampleList) {
        mExampleList = exampleList;
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.scoreboard_recycler, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        ScoreboardItem currentItem = mExampleList.get(position);

        holder.nameText.setText(currentItem.getNameText());
        holder.scoreText.setText(String.valueOf(currentItem.getScoreValue()));
    }

    @Override
    public int getItemCount() {
        /*return mExampleList.size();*/
        return mExampleList.size();
    }
}
