package com.example.e3646.lifeblabla.account;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.e3646.lifeblabla.R;
import com.example.e3646.lifeblabla.main.MainAdapter;
import com.example.e3646.lifeblabla.object.Account;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter {

    private ArrayList<Account> mAccountList;

    public AccountAdapter(ArrayList<Account> accountList) {
        mAccountList = accountList;
        if (accountList != null) {
            Log.d("account list", "size" + accountList.size());
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new AccountAdapter.ItemViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_account, null));

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView mCategory;
        private TextView mDescription;
        private TextView mRevenueOrExpense;
        private TextView mAmount;



        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            mCategory = itemView.findViewById(R.id.item_category);
            mDescription = itemView.findViewById(R.id.item_description);
            mRevenueOrExpense = itemView.findViewById(R.id.item_revenue_expense);
            mAmount = itemView.findViewById(R.id.item_amount);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        if (mAccountList != null) {

            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;

            if (mAccountList.get(i).getCategory().equals("1")) {
                itemViewHolder.mCategory.setText("吃飯");
            } else if (mAccountList.get(i).getCategory().equals("2")) {
                itemViewHolder.mCategory.setText("交通");
            } else if (mAccountList.get(i).getCategory().equals("3")) {
                itemViewHolder.mCategory.setText("購物");
            } else if (mAccountList.get(i).getCategory().equals("4")) {
                itemViewHolder.mCategory.setText("娛樂");
            } else if (mAccountList.get(i).getCategory().equals("5")) {
                itemViewHolder.mCategory.setText("雜支");
            }

            if (mAccountList.get(i).getRevenue().length() > 0) {
                itemViewHolder.mRevenueOrExpense.setText("收入");
                itemViewHolder.mAmount.setText(mAccountList.get(i).getRevenue());
                itemViewHolder.mRevenueOrExpense.setTextColor(Color.parseColor("#166C2D"));

                itemViewHolder.mAmount.setTextColor(Color.parseColor("#166C2D"));
                Log.d("revenue", "amount: " + mAccountList.get(i).getRevenue());
            } else {
                itemViewHolder.mRevenueOrExpense.setText("支出");
                itemViewHolder.mRevenueOrExpense.setTextColor(Color.parseColor("#892222"));
                itemViewHolder.mAmount.setText(mAccountList.get(i).getExpense());
                itemViewHolder.mAmount.setTextColor(Color.parseColor("#892222"));

                Log.d("expense", "amount: " + mAccountList.get(i).getExpense());
            }

        }


    }

    @Override
    public int getItemCount() {
        if (mAccountList != null) {
            return mAccountList.size();
        } else {
            return 0;
        }
    }

    public void setAccountList(ArrayList<Account> accountList) {
        mAccountList = accountList;
    }

}
