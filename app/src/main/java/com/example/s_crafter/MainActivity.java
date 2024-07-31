package com.example.s_crafter;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.s_crafter.adapter.GalleryAdapter;
import com.example.s_crafter.adapter.NavigatorAdapter;
import com.example.s_crafter.model.Gallery;
import com.example.s_crafter.model.Navigation;

import java.util.ArrayList;
import java.util.List;

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
        testDb();
    }

    private void testDb() {
        for (int i = 0; i < 20; i++) {
            navigationList.add(new Navigation(i + 1, String.valueOf(i + 1)));
        }
        setNavigatorRecycler(navigationList);

        for (int i = 0; i < 20; i++) {
            galleryList.add(new Gallery(i + 1, "my_image", "title", i + 1 + "__" + textTest));
        }
        setGalleryRecycler(galleryList);
    }

    private void setGalleryRecycler(List<Gallery> galleryList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        galleryRecycler = findViewById(R.id.galleryRecycler);
        galleryRecycler.setLayoutManager(layoutManager);

        galleryAdapter = new GalleryAdapter(this, galleryList);
        galleryRecycler.setAdapter(galleryAdapter);
    }

    private void setNavigatorRecycler(List<Navigation> navigationList) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        navigatorRecycler = findViewById(R.id.navigatorRecycler);
        navigatorRecycler.setLayoutManager(layoutManager);

        navigatorAdapter = new NavigatorAdapter(this, navigationList);
        navigatorRecycler.setAdapter(navigatorAdapter);
    }
}