package com.example.s_crafter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class FlipCard extends AppCompatActivity {
    private FrameLayout cardContainer;
    private View cardFront;
    private View cardBack;
    private boolean isFrontVisible = true;
    private boolean isAnimating = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_flip_card);
        View contBg = findViewById(R.id.bg_flip_card);
        contBg.setBackgroundColor(getResources().getColor(R.color.bg_app_color));
        cardContainer = findViewById(R.id.card_container);
        cardFront = getLayoutInflater().inflate(R.layout.card_front, null);
        cardBack = getLayoutInflater().inflate(R.layout.card_back, null);
        cardBack.setBackgroundColor(getResources().getColor(R.color.bg_card_color));
        ImageView imageView = cardFront.findViewById(R.id.image_front);

        String storyPageImagePath = getIntent().getStringExtra("storyPageImagePathFlipCard");
        if (storyPageImagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(storyPageImagePath);
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.my_image);
        }
        String storyPageText = getIntent().getStringExtra("storyPageTextFlipCard");
        setTextOnCardBack(storyPageText);
        adjustCardSize();
        cardContainer.addView(cardFront);
        cardContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isAnimating) {
                    flipCard();
                }
            }
        });
    }

    private void setTextOnCardBack(String text) {
        TextView textView = cardBack.findViewById(R.id.text_back);
        textView.setTextColor(Color.parseColor(getString(R.string.cardTextColor)));
        textView.setText(text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        } else {
            textView.setGravity(Gravity.START);
        }
    }

    private void setImage(View view, int imageViewId, int imageResource) {
        ImageView imageView = view.findViewById(imageViewId);
        imageView.setImageResource(imageResource);
    }

    private void adjustCardSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        int cardWidth = (int) (width * 0.75);
        int cardHeight = (int) (height * 0.75);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(cardWidth, cardHeight);
        params.gravity = android.view.Gravity.CENTER;
        cardContainer.setLayoutParams(params);
    }

    private void flipCard() {
        isAnimating = true;
        final ViewPropertyAnimator animator = cardContainer.animate();
        animator.setDuration(200);
        animator.setInterpolator(new DecelerateInterpolator());
        if (isFrontVisible) {
            animator.rotationY(90).withEndAction(new Runnable() {
                @Override
                public void run() {
                    cardContainer.removeView(cardFront);
                    cardContainer.addView(cardBack);
                    cardContainer.setRotationY(-90);
                    cardContainer.animate().rotationY(0).setDuration(300).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            isAnimating = false;
                        }
                    }).start();
                }
            }).start();
        } else {
            animator.rotationY(90).withEndAction(new Runnable() {
                @Override
                public void run() {
                    cardContainer.removeView(cardBack);
                    cardContainer.addView(cardFront);
                    cardContainer.setRotationY(-90);
                    cardContainer.animate().rotationY(0).setDuration(300).withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            isAnimating = false;
                        }
                    }).start();
                }
            }).start();
        }
        isFrontVisible = !isFrontVisible;
    }
}