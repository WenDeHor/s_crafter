package com.example.s_crafter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_crafter.adapter.CenterSmoothScroller;
import com.example.s_crafter.adapter.GalleryAdapter;
import com.example.s_crafter.adapter.NavigatorAdapter;
import com.example.s_crafter.model.Gallery;
import com.example.s_crafter.model.Navigation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    RecyclerView navigatorRecycler, galleryRecycler;
    NavigatorAdapter navigatorAdapter;
    GalleryAdapter galleryAdapter;
    List<Navigation> navigationList = new ArrayList<>();
    List<Gallery> galleryList = new ArrayList<>();


    String textTest = "Інколи в житі кожної людини відбуваються події про які вона могла лише мріяти. Життєва стіна була непробивною і тому кожен усвідомивши це продовжував жити сірими буднями з дня в день займаючись тими самими справами, не змінюючи свого звичного способу життя. Кожна подія була передбачуваною і навіть подарунок, що містив в собі фактор несподіваності був очікуваним і не містив в собі нічого надзвичайного";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        galleryRecycler = findViewById(R.id.galleryRecycler);
        navigatorRecycler = findViewById(R.id.navigatorRecycler);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        addStoryByButton(buttonAdd);

        testDb(galleryRecycler, navigatorRecycler);

        NavigatorAdapter searcher = new NavigatorAdapter(this, navigationList, new NavigatorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                smoothScrollToCenter(galleryRecycler, position);
            }
        });
        navigatorRecycler.setAdapter(searcher);

    }

    private void addStoryByButton(Button buttonAdd) {
        buttonAdd.setBackgroundColor(Color.parseColor("#ADD8E6"));
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                Intent intent = new Intent(MainActivity.this, AddStory.class);
                startActivity(intent);
            }
        });
    }

    private void testDb(RecyclerView galleryRecycler, RecyclerView navigatorRecycler) {
        for (int i = 0; i < 20; i++) {
            navigationList.add(new Navigation(i + 1, String.valueOf(i + 1)));
        }
        setNavigatorRecycler(navigationList, navigatorRecycler);

        for (int i = 0; i < 20; i++) {
            galleryList.add(new Gallery(i + 1, "my_image", "title", i + 1 + "__" + textTest));
        }
        setGalleryRecycler(galleryList, galleryRecycler);
    }

    private void setGalleryRecycler(List<Gallery> galleryList, RecyclerView galleryRecycler) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        galleryRecycler.setLayoutManager(layoutManager);

        galleryAdapter = new GalleryAdapter(this, galleryList);
        galleryRecycler.setAdapter(galleryAdapter);
    }

    private void setNavigatorRecycler(List<Navigation> navigationList, RecyclerView navigatorRecycler) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        navigatorRecycler.setLayoutManager(layoutManager);

        navigatorAdapter = new NavigatorAdapter(this, navigationList);
        navigatorRecycler.setAdapter(navigatorAdapter);
    }

    private void smoothScrollToCenter(RecyclerView recyclerView, int position) {
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(this);
        smoothScroller.setTargetPosition(position);
        Objects.requireNonNull(recyclerView.getLayoutManager()).startSmoothScroll(smoothScroller);
    }
}