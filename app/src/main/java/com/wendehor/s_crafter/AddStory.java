package com.wendehor.s_crafter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.wendehor.s_crafter.model.StoryEntity;
import com.wendehor.s_crafter.repository.AppDatabase;
import com.wendehor.s_crafter.repository.StoryDao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        editText = findViewById(R.id.addingText);
        int maxLength = 250;
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        Button buttonSelectImage = findViewById(R.id.buttonSelectImage);
        buttonSelectImage.setBackgroundColor(getResources().getColor(R.color.button_color));
        imageView = findViewById(R.id.imageStoryPage);

        reloadSelectedImage(buttonSelectImage);

        Button buttonSave = findViewById(R.id.buttonSaveStory);
        buttonSave.setBackgroundColor(getResources().getColor(R.color.button_color));
        buttonSave.setOnClickListener(v -> saveStory());
    }

    private void reloadSelectedImage(Button button) {
        button.setOnClickListener(e -> openGallery());
    }

    private void saveStory() {
        AppDatabase db = AppDatabase.getInstance(AddStory.this);
        StoryDao storyDao = db.storyDao();
        String editTextString = editText.getText().toString().trim();
        String imageUriPath = null;

        if (imageUri != null) {
            imageUriPath = imageUri.getPath();
        }

        if (imageUriPath != null && !editTextString.isEmpty()) {
            Executor executor = Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    String imagePath = saveImageToFile(bitmap, this);
                    if (imagePath != null) {
                        StoryEntity story = new StoryEntity(imagePath, editTextString, false, 0);
                        storyDao.insert(story);
                        runOnUiThread(() -> {
                            notification("Event saved successfully");
                            Intent intent = new Intent(AddStory.this, MainActivity.class);
                            startActivity(intent);
                        });
                    } else {
                        runOnUiThread(() -> notification("Error saving image"));
                    }
                } catch (Exception e) {
                    runOnUiThread(() -> notification("Error saving event"));
                    e.printStackTrace();
                }
            });
        } else {
            notification("Choose a photo and add a description");
        }
    }

    private String saveImageToFile(Bitmap bitmap, Context context) {
        File directory = context.getDir("images", Context.MODE_PRIVATE);
        String fileName = "image_" + System.currentTimeMillis() + ".png";
        File file = new File(directory, fileName);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return file.getAbsolutePath();
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