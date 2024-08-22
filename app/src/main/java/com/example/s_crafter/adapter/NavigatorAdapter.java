package com.example.s_crafter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_crafter.R;
import com.example.s_crafter.model.Navigation;

import java.util.List;

public class NavigatorAdapter extends RecyclerView.Adapter<NavigatorAdapter.NavigatorViewHolder> {

    private final Context context;
    private final List<Navigation> navigations;
    private final OnItemClickListener onItemClickListener;

    public NavigatorAdapter(Context context, List<Navigation> navigations, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.navigations = navigations;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public NavigatorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View navigatorItems = LayoutInflater.from(context).inflate(R.layout.navigation_item, parent, false);
        return new NavigatorViewHolder(navigatorItems);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigatorViewHolder holder, int position) {
        holder.navigationTitle.setText(navigations.get(position).getTitle());
        holder.itemView.setOnClickListener(v -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return navigations.size();
    }

    public static final class NavigatorViewHolder extends RecyclerView.ViewHolder {
        TextView navigationTitle;

        public NavigatorViewHolder(@NonNull View itemView) {
            super(itemView);
            navigationTitle = itemView.findViewById(R.id.navigationTitleItem);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}
