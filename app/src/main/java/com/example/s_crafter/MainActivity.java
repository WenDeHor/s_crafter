package com.example.s_crafter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_crafter.adapter.CenterSmoothScroller;
import com.example.s_crafter.adapter.GalleryAdapter;
import com.example.s_crafter.adapter.NavigatorAdapter;
import com.example.s_crafter.model.Navigation;
import com.example.s_crafter.model.StoryEntity;
import com.example.s_crafter.repository.AppDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {
    private RecyclerView navigatorRecycler, galleryRecycler;
    private NavigatorAdapter navigatorAdapter;
    private GalleryAdapter galleryAdapter;
    private List<StoryEntity> storyList = new ArrayList<>();
    private List<Navigation> navigationList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        galleryRecycler = findViewById(R.id.galleryRecycler);
        navigatorRecycler = findViewById(R.id.navigatorRecycler);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        addStoryByButton(buttonAdd);
        initializeAdapters();
        setupViewModel();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupViewModel();
                initializeAdapters();
            } else {
                Toast.makeText(this, "Permission denied to read external storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initializeAdapters() {
        navigatorAdapter = new NavigatorAdapter(this, navigationList, position -> smoothScrollToCenter(galleryRecycler, position));
        galleryAdapter = new GalleryAdapter(this, storyList);
        RecyclerView.LayoutManager layoutManagerGalleryRecycler = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        galleryRecycler.setLayoutManager(layoutManagerGalleryRecycler);
        RecyclerView.LayoutManager layoutManagerNavigatorRecycler = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        navigatorRecycler.setLayoutManager(layoutManagerNavigatorRecycler);
        navigatorRecycler.setAdapter(navigatorAdapter);
        galleryRecycler.setAdapter(galleryAdapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void setupViewModel() {
        AppDatabase db = AppDatabase.getInstance(this);
        db.storyDao().getAllImages().observe(this, storyEntities -> {
            storyList.clear();
            navigationList.clear();
            storyList.addAll(storyEntities);
            navigationList.addAll(storyEntities.stream()
                    .map(e -> new Navigation(e.getId(), String.valueOf(e.getId())))
                    .collect(Collectors.toList()));
            if (storyList.isEmpty()) {
                savePathDefaultImageToStorage();
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File imageFile = new File(storageDir, "mine_photo_list.jpg");

                storyList.add(new StoryEntity(imageFile.getPath(), "add your story", false, 0));
                navigationList.add(new Navigation(1, "1"));
            }
            galleryAdapter.notifyDataSetChanged();
            navigatorAdapter.notifyDataSetChanged();
        });
    }

    private void addStoryByButton(Button buttonAdd) {
        buttonAdd.setBackgroundColor(getResources().getColor(R.color.button_color));
        buttonAdd.setOnClickListener(v -> {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            Intent intent = new Intent(MainActivity.this, AddStory.class);
            startActivity(intent);
        });
    }

    private void smoothScrollToCenter(RecyclerView recyclerView, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(this);
        smoothScroller.setTargetPosition(position);
        Objects.requireNonNull(recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);
    }

    private void savePathDefaultImageToStorage() {
        File storageDir;
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.mine_photo_list);
            storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (storageDir != null && !storageDir.exists()) {
                storageDir.mkdirs();
            }
            File imageFile = new File(storageDir, "mine_photo_list.jpg");
            if (!imageFile.exists()) {
                imageFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


