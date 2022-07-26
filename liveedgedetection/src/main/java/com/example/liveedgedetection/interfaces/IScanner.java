package com.example.liveedgedetection.interfaces;

import android.graphics.Bitmap;

import com.example.liveedgedetection.enums.ScanHint;

/**
 * Interface between activity and surface view
 */

public interface IScanner {
    void displayHint(ScanHint scanHint);
    void onPictureClicked(Bitmap bitmap);
}
