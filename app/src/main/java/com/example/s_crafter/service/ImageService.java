//package com.example.s_crafter.service;
//
//import android.annotation.SuppressLint;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//
//import android.provider.MediaStore;
//
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.List;
//
//public class ImageService extends AppCompatActivity {
//
//    private List<Bitmap> loadAllImages() {
//        List<Bitmap> bitmaps = new ArrayList<>();
//        File directory = getFilesDir();
//        File[] files = directory.listFiles();
//
//        if (files != null) {
//            for (File file : files) {
//                if (file.isFile() && (file.getName().endsWith(".jpg") || file.getName().endsWith(".png"))) {
//                    Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//                    bitmaps.add(bitmap);
//                }
//            }
//        }
//        return bitmaps;
//    }
//
//    private void saveImageToInternalStorage(Uri imageUri) throws IOException {
//        @SuppressLint("Recycle")
//        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
//
//        File directory = getFilesDir();
//        File file = new File(directory, "saved_image.jpg");
//
//        FileOutputStream fos = new FileOutputStream(file);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//        fos.close();
//    }
//}
