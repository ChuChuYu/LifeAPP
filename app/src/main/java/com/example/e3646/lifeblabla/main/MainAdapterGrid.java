package com.example.e3646.lifeblabla.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.e3646.lifeblabla.R;

import java.util.ArrayList;
import java.util.List;

public class MainAdapterGrid extends RecyclerView.Adapter {

    private Context mContext;
    private boolean isListLayout = false;

    private List<Integer> heights = new ArrayList<>();

    public MainAdapterGrid (Context context) {
        this.mContext = context;
        getRandomHeight(30);

    }

    private void getRandomHeight(int n){
        heights = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            heights.add((int)( 400 + Math.random() * 400));
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (isListLayout) {
            return new MainAdapterGrid.MainListItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_list, null));
        } else {

            return new MainAdapterGrid.MainGridItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_grid, null));

        }
    }

    public class MainListItemViewHolder extends RecyclerView.ViewHolder {

        public MainListItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public class MainGridItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView mCardView;

        public MainGridItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mCardView = (ImageView)itemView.findViewById(R.id.card_view);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        MainGridItemViewHolder mainGridItemViewHolder = (MainGridItemViewHolder)viewHolder;
        ViewGroup.LayoutParams layoutParams = mainGridItemViewHolder.mCardView.getLayoutParams();

        layoutParams.height = heights.get(i);

//        ViewGroup.LayoutParams layoutParamsofText = mainGridItemViewHolder.mCardViewImage.getLayoutParams();
//        layoutParamsofText.height = heights.get(i)/2;
        mainGridItemViewHolder.mCardView.setLayoutParams(layoutParams);
//        mainGridItemViewHolder.mCardViewImage.setLayoutParams(layoutParamsofText);

    }

    @Override
    public int getItemCount() {
        return 30;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
