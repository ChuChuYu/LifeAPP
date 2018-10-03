package com.example.e3646.lifeblabla.account;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.main.MainAdapter;

public class AccountAddAdapter extends RecyclerView.Adapter {

    private int ACCOUNT_NUMBER = 1;

    public void setACCOUNTNUMBER(int accountNumber) {
        ACCOUNT_NUMBER = accountNumber;
    }

    public AccountAddAdapter(int accountNumber) {

        ACCOUNT_NUMBER = accountNumber;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AccountAddAdapter.ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_account_edit, null));

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {



        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return ACCOUNT_NUMBER;
    }

}