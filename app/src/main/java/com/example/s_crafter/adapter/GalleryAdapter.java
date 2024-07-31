package com.example.s_crafter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_crafter.R;
import com.example.s_crafter.StoryPage;
import com.example.s_crafter.model.Gallery;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    Context context;
    List<Gallery> galleries;

    public GalleryAdapter(Context context, List<Gallery> galleries) {
        this.context = context;
        this.galleries = galleries;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View galleryItems = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new GalleryAdapter.GalleryViewHolder(galleryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        int imageId = context.getResources().getIdentifier(galleries.get(position).getImg(), "drawable", context.getPackageName());
        holder.imageView.setImageResource(imageId);
        String text = galleries.get(position).getText();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, StoryPage.class);
                intent.putExtra("storyPageImage", imageId);
                intent.putExtra("storyPageText", text);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galleries.size();
    }

    public static final class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.galleryImg);
        }
    }
}
