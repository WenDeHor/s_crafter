package com.example.s_crafter;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        cardContainer = findViewById(R.id.card_container);
        cardFront = getLayoutInflater().inflate(R.layout.card_front, null);
        cardBack = getLayoutInflater().inflate(R.layout.card_back, null);

        // Завантаження зображень для передньої і задньої сторін картки з ресурсів
        setImage(cardFront, R.id.image_front, R.drawable.my_image); // Замість "my_image" вкажіть ім'я вашого зображення
        setImage(cardBack, R.id.image_back, R.drawable.my_image); // Замість "my_image" вкажіть ім'я вашого зображення

        // Налаштування розміру картки
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

    private void setImage(View view, int imageViewId, int imageResource) {
        ImageView imageView = view.findViewById(imageViewId);
        imageView.setImageResource(imageResource);
    }

    private void adjustCardSize() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        // Встановити розмір картки на 75% від ширини і висоти екрану
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