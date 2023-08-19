package com.example.todolist.ui;

import android.content.Context;
import androidx.core.content.ContextCompat;
import com.example.todolist.R;
import com.example.todolist.data.FilledType;

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
                return ContextCompat.getColor(context, R.color.green_700);
            case PARTIALLY:
                return ContextCompat.getColor(context, R.color.blue_700);
            case OPTIONAL:
                return ContextCompat.getColor(context, R.color.gray_700);
            default:
                return ContextCompat.getColor(context, R.color.red_700);
        }
    }
}
