package com.sang.ailatrieuphu.presentation.view.features.highscore;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.sang.ailatrieuphu.R;
import com.sang.ailatrieuphu.data.entity.HighScore;
import com.sang.ailatrieuphu.databinding.HighscoreItemBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

import static android.os.Build.VERSION_CODES.R;

public class HighScoreAdapter extends RecyclerView.Adapter<HighScoreAdapter.ViewHolder> {
    private final ArrayList<HighScore> lsHighScore;


    public HighScoreAdapter(ArrayList<HighScore> lsHighScore){
        this.lsHighScore = lsHighScore;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.lsHighScore.sort(new Comparator<HighScore>() {
                @Override
                public int compare(HighScore o1, HighScore o2) {
                    return o2.getScore() - o1.getScore();
                }
            });
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        HighscoreItemBinding binding = HighscoreItemBinding.inflate(inflater, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return this.lsHighScore.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        HighscoreItemBinding itemBinding;
        public ViewHolder(HighscoreItemBinding binding) {
            super(binding.getRoot());

            itemBinding = binding;
        }

        public void bindView(int position){
            HighScore highScore = lsHighScore.get(position);
            switch (getAdapterPosition() + 1){
                case 1:{
                    itemBinding.ivCup.setVisibility(View.VISIBLE);
                    itemBinding.txtNumber.setVisibility(View.INVISIBLE);
                    itemBinding.ivCup.setImageResource(com.sang.ailatrieuphu.R.drawable.rank_1);
                    itemBinding.getRoot().setBackgroundColor(Color.parseColor("#CC00CC"));
                    break;
                }
                case 2:{
                    itemBinding.ivCup.setVisibility(View.VISIBLE);
                    itemBinding.txtNumber.setVisibility(View.INVISIBLE);
                    itemBinding.ivCup.setImageResource(com.sang.ailatrieuphu.R.drawable.rank_2);
                    itemBinding.getRoot().setBackgroundColor(Color.parseColor("#009966"));
                    break;
                }
                case 3:{
                    itemBinding.txtNumber.setVisibility(View.INVISIBLE);
                    itemBinding.ivCup.setVisibility(View.VISIBLE);
                    itemBinding.ivCup.setImageResource(com.sang.ailatrieuphu.R.drawable.rank_3);
                    itemBinding.getRoot().setBackgroundColor(Color.parseColor("#0099FF"));
                    break;
                }
                default:{
                    itemBinding.txtNumber.setVisibility(View.VISIBLE);
                    itemBinding.txtNumber.setText((getAdapterPosition() + 1) + "");
                    itemBinding.ivCup.setVisibility(View.INVISIBLE);
                    itemBinding.getRoot().setBackgroundColor(Color.TRANSPARENT);
                }
            }
            itemBinding.txtMoney.setText(formatScore(highScore.getScore()) + " VND");
            itemBinding.txtPlayerName.setText(lsHighScore.get(position).getName());
        }

        private String formatScore(int score){
            String money = "";
            if (score == 0){
                return "0";
            }
          String str = score + "";
            for (int i = str.length(); i >= 0; i -= 3){
                if (i > 3){
                    money = "," + str.substring(i - 3, i) + money;
                }else {
                    money = str.substring(0, i) + money;
                }

            }

            return money;
        }

    }
}
