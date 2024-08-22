package com.example.s_crafter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StoryPage extends AppCompatActivity {
    private String storyPageImagePath;
    private String storyPageText;

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
        Button buttonFlip = findViewById(R.id.buttonToFlip);
        buttonFlip.setBackgroundColor(getResources().getColor(R.color.button_color));
        toFlipCardActivityByButton(buttonFlip);
    }

    private void toFlipCardActivityByButton(Button buttonFlip) {
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
