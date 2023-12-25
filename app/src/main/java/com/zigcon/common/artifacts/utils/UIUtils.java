package com.zigcon.common.artifacts.utils;

import android.content.Context;
import android.view.Window;
import android.widget.Toast;

import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

/**
 * @Author Dharmesh
 * @Date 05-03-2022
 * <p>
 * Information
 **/
public class UIUtils {

    /**
     * Show keyboard for backward API Supported
     */
    public static void showKeyboard(Window window) {
        new WindowInsetsControllerCompat(window, window.getDecorView()).show(WindowInsetsCompat.Type.ime());
    }

    /**
     * Hide keyboard for backward API Supported
     */
    public static void hideKeyboard( Window window) {
        new WindowInsetsControllerCompat(window, window.getDecorView()).hide(WindowInsetsCompat.Type.ime());
    }

    public static void showToast(Context context,int strResId){
        Toast.makeText(context, strResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context,String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
