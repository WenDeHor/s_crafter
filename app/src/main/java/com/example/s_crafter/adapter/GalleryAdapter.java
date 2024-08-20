package com.example.s_crafter.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_crafter.FlipCard;
import com.example.s_crafter.R;
import com.example.s_crafter.StoryPage;
import com.example.s_crafter.model.Gallery;
import com.example.s_crafter.model.StoryEntity;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {
    Context context;
    List<StoryEntity> stories;

    public GalleryAdapter(Context context, List<StoryEntity> stories) {
        this.context = context;
        this.stories = stories;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View galleryItems = LayoutInflater.from(context).inflate(R.layout.gallery_item, parent, false);
        return new GalleryAdapter.GalleryViewHolder(galleryItems);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        StoryEntity story = stories.get(position);

        Bitmap bitmap = BitmapFactory.decodeFile(story.getImagePath());
        holder.imageView.setImageBitmap(bitmap);

        String text = story.getDescription();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentStoryPage = new Intent(context, StoryPage.class);
                intentStoryPage.putExtra("storyPageImage", story.getImagePath());
                intentStoryPage.putExtra("storyPageText", text);


                context.startActivity(intentStoryPage, imageTransitionMaker(holder).toBundle());
            }
        });
    }


    private ActivityOptions imageTransitionMaker(@NonNull GalleryViewHolder holder) {
        return ActivityOptions.makeSceneTransitionAnimation(
                (Activity) context,
                new Pair<View, String>(holder.imageView, "imageTransition")
        );
    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public static final class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.galleryImg);
        }
    }
}

