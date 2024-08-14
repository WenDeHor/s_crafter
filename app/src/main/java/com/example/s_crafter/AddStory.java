package com.example.s_crafter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputFilter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s_crafter.model.StoryEntity;
import com.example.s_crafter.repository.AppDatabase;
import com.example.s_crafter.repository.StoryDao;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AddStory extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView imageView;
    private Uri imageUri;
    private TextView editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_story);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        editText = findViewById(R.id.addingText);
        int maxLength = 250;
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);
        imageView = findViewById(R.id.imageStoryPage);

        reloadSelectedImage(buttonSelectImage);

        Button buttonSave = findViewById(R.id.buttonSaveStory);
        buttonSave.setOnClickListener(v -> saveStory());
    }

    private void reloadSelectedImage(Button button) {
        button.setOnClickListener(v -> openGallery());
    }

    private void saveStory() {
        AppDatabase db = AppDatabase.getInstance(AddStory.this);
        StoryDao imageDao = db.imageDao();
        String editTextString = editText.getText().toString();
        String imageUriPath = "null";
        try {
            imageUriPath = imageUri.getPath();
        } catch (NullPointerException e) {
            imageUriPath = "null";
        }

        if (!Objects.equals(imageUriPath, "null") && !editTextString.isEmpty()) {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                StoryEntity image = new StoryEntity(imageUri.getPath(), editText.toString(), false, 0);
                imageDao.insert(image);
                notification("Подія збережена успішно");
                Intent intent = new Intent(AddStory.this, MainActivity.class);
                startActivity(intent);
            });
        } else {
            notification("Виберіть фото і добавте опис");
        }
    }

    private void notification(String notification) {
        runOnUiThread(() -> {
            Toast.makeText(this, notification, Toast.LENGTH_SHORT).show();
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {
            imageUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}