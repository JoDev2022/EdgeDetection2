package com.example.edgedetection2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;

import com.example.liveedgedetection.activity.ScanActivity;
import com.example.liveedgedetection.constants.ScanConstants;
import com.example.liveedgedetection.util.ScanUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 101;
    private ImageView scannedImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scannedImageView = findViewById(R.id.scanned_image);
        startScan();
    }

    private void startScan() {

        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("imageId", "1");
        intent.putExtra("bolId", "2");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                if(null != data && null != data.getExtras()) {
                    //String pathOriginal = data.getStringExtra("imageId");
                    String test2 = data.getStringExtra("bolId");
                    String filePath = data.getExtras().getString(ScanConstants.SCANNED_RESULT);
                    Bitmap baseBitmap = ScanUtils.decodeBitmapFromFile(filePath, ScanConstants.IMAGE_NAME);
//                    scannedImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    scannedImageView.setImageBitmap(baseBitmap);


                    //Bitmap orignalBitmap = ScanUtils.decodeBitmapFromFile(pathOriginal, "Image_original");

                    ByteArrayOutputStream blob = new ByteArrayOutputStream();
                    baseBitmap.compress(Bitmap.CompressFormat.PNG, 100, blob);
                    byte[] copiedImage = blob.toByteArray();
                   // orignalBitmap.compress(Bitmap.CompressFormat.PNG, 0, blob);
                    //byte[] originalImage = blob.toByteArray();
//
                    saveImage(copiedImage, "Copied_Image");
                   // saveImage(originalImage, "Original_Image");

                }
            } else if(resultCode == Activity.RESULT_CANCELED) {
                finish();
            }
        }
    }

    private void saveImage(byte[] image, String filename){
        String fname = filename + ".png";
        File destination = new File(this.getApplicationContext().getFilesDir(), "BOLImages");
        if (!destination.exists()) {
            destination.mkdirs();
        }
        File file = new File(destination, fname);
        if (file.exists()) {
            file.delete();
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            out.write(image);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}