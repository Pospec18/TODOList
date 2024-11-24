package com.pospecstudio.todolist.ui;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.example.todolist.R;
import com.pospecstudio.todolist.data.FilledType;

public class FilledTypeToColor {
    public static int primary(FilledType type, Context context) {
        switch (type) {
            case FULLY:
                return ContextCompat.getColor(context, R.color.green_500);
            case PARTIALLY:
                return ContextCompat.getColor(context, R.color.blue_500);
            case OPTIONAL:
                return ContextCompat.getColor(context, R.color.gray_500);
            default:
                return ContextCompat.getColor(context, R.color.red_500);
        }
    }

    public static int secondary(FilledType type, Context context) {
        switch (type) {
            case FULLY:
                return ContextCompat.getColor(context, R.color.green_600);
            case PARTIALLY:
                return ContextCompat.getColor(context, R.color.blue_600);
            case OPTIONAL:
                return ContextCompat.getColor(context, R.color.gray_600);
            default:
                return ContextCompat.getColor(context, R.color.red_600);
        }
    }

    public static int darker(FilledType type, Context context) {
        switch (type) {
            case FULLY:
                return ContextCompat.getColor(context, R.color.green_700);
            case PARTIALLY:
                return ContextCompat.getColor(context, R.color.blue_700);
            case OPTIONAL:
                return ContextCompat.getColor(context, R.color.gray_700);
            default:
                return ContextCompat.getColor(context, R.color.red_700);
        }
    }

    public static int semitransparent(FilledType type, Context context) {
        switch (type) {
            case FULLY:
                return ContextCompat.getColor(context, R.color.green_500_50);
            case PARTIALLY:
                return ContextCompat.getColor(context, R.color.blue_500_50);
            case OPTIONAL:
                return ContextCompat.getColor(context, R.color.gray_500_50);
            default:
                return ContextCompat.getColor(context, R.color.red_500_50);
        }
    }

    public static int transparent(FilledType type, Context context) {
        switch (type) {
            case FULLY:
                return ContextCompat.getColor(context, R.color.green_200);
            case PARTIALLY:
                return ContextCompat.getColor(context, R.color.blue_200);
            case OPTIONAL:
                return ContextCompat.getColor(context, R.color.gray_200);
            default:
                return ContextCompat.getColor(context, R.color.red_200);
        }
    }
}
