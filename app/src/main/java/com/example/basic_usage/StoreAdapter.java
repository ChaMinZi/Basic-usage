package com.example.basic_usage;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basic_usage.model.Store;

import java.util.ArrayList;
import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreViewHolder> {

    private List<Store> mItems = new ArrayList<>();

    @NonNull
    @Override
    public StoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new StoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreViewHolder holder, int position) {
        Store store = mItems.get(position);
        holder.nameTextView.setText(store.getName());
        holder.addressTextView.setText(store.getAddr());

        String remainStat = "충분";
        String count = "100개 이상";
        int color = Color.GREEN;
        switch (store.getRemainStat()) {
            case "plenty":
                count = "100개 이상";
                remainStat = "충분";
                color = Color.GREEN;
                break;
            case "safe":
                count = "30개 이상";
                remainStat = "여유";
                color = Color.YELLOW;
                break;
            case "few":
                count = "2개 이상";
                remainStat = "매진 임박";
                color = Color.RED;
                break;
            case "empty":
                count = "1개 이하";
                remainStat = "재고 없음";
                color = Color.GRAY;
                break;
            default:
        }

        holder.distanceTextView.setText(String.format("%.2fkm", store.getDistance()));
        holder.countTextView.setText(count);
        holder.countTextView.setTextColor(color);
        holder.remainTextView.setText(remainStat);
        holder.remainTextView.setTextColor(color);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // Item View 정보를 가지고 있는 클래스
    static class StoreViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView distanceTextView;
        TextView remainTextView;
        TextView countTextView;

        public StoreViewHolder(@NonNull View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name_text_view);
            addressTextView = itemView.findViewById(R.id.addr_text_view);
            distanceTextView = itemView.findViewById(R.id.distacne_text_view);
            remainTextView = itemView.findViewById(R.id.remain_text_view);
            countTextView = itemView.findViewById(R.id.count_text_view);
        }
    }

    public void updateItems(List<Store> items) {
        mItems = items;
        notifyDataSetChanged();         // UI 갱신
    }
}
