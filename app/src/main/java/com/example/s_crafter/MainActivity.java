package com.example.s_crafter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_crafter.adapter.CenterSmoothScroller;
import com.example.s_crafter.adapter.GalleryAdapter;
import com.example.s_crafter.adapter.NavigatorAdapter;
import com.example.s_crafter.model.Navigation;
import com.example.s_crafter.model.StoryEntity;
import com.example.s_crafter.repository.AppDatabase;

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
        EdgeToEdge.enable(this);  // Якщо EdgeToEdge є частиною вашого проєкту
        setContentView(R.layout.activity_main);

        galleryRecycler = findViewById(R.id.galleryRecycler);
        navigatorRecycler = findViewById(R.id.navigatorRecycler);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        addStoryByButton(buttonAdd);
        initializeAdapters();
        setupViewModel();
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    1);
        } else {
            initializeAdapters();
            setupViewModel();
        }
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

            galleryAdapter.notifyDataSetChanged();
            navigatorAdapter.notifyDataSetChanged();
        });
    }

    private void addStoryByButton(Button buttonAdd) {
        buttonAdd.setBackgroundColor(Color.parseColor("#ADD8E6"));
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
}


