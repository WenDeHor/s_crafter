package com.example.s_crafter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.s_crafter.model.StoryEntity;

public class StoryPage extends AppCompatActivity {
    private Context context;
    String storyPageImagePath;
    String storyPageText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_story_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.storyPage), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView imageView = findViewById(R.id.imageStoryPageRead);

        storyPageImagePath = getIntent().getStringExtra("storyPageImage");
        if (storyPageImagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(storyPageImagePath);
            imageView.setImageBitmap(bitmap);
        } else {
             imageView.setImageResource(R.drawable.my_image);
        }

        TextView textView = findViewById(R.id.textStoryPage);
        storyPageText = getIntent().getStringExtra("storyPageText");
        textView.setText(storyPageText);

        Button backButton = findViewById(R.id.buttonRevert);
        buttonRevert(backButton);

        Button buttonFlip = findViewById(R.id.buttonToFlip);
        toFlipCardActivityByButton(buttonFlip);
    }


    private void buttonRevert(Button backButton) {
        backButton.setBackgroundColor(Color.parseColor("#ADD8E6"));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void toFlipCardActivityByButton(Button buttonFlip) {
        buttonFlip.setBackgroundColor(Color.parseColor("#ADD8E6"));
        buttonFlip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFlipCard = new Intent(StoryPage.this, FlipCard.class);
                intentFlipCard.putExtra("storyPageImagePathFlipCard", storyPageImagePath);
                intentFlipCard.putExtra("storyPageTextFlipCard", storyPageText);
                startActivity(intentFlipCard);
            }
        });
    }
}
