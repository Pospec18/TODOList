package com.example.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.todolist.MainActivity;
import com.example.todolist.R;
import com.example.todolist.SwitchListActivity;

public class ListView extends LinearLayout {

    public ListView(Context context, String listName, SwitchListActivity activity) {
        super(context);
        setBackgroundResource(R.drawable.item_background);
        ((GradientDrawable)getBackground()).setColor(ContextCompat.getColor(context, R.color.blue_500));

        LayoutParams a = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        a.setMargins(10, 10, 10, 10);
        a.gravity = Gravity.CENTER;
        setLayoutParams(a);

        LayoutParams nameLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 10);
        nameLayoutParams.gravity = Gravity.CENTER;
        nameLayoutParams.setMargins(50, 10, 0, 10);
        LayoutParams countLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
        countLayoutParams.gravity = Gravity.CENTER;
        countLayoutParams.setMargins(0, 10, 0, 10);

        ContextThemeWrapper textThemeWrapper = new ContextThemeWrapper(context, R.style.text);
        TextView nameView = new TextView(textThemeWrapper, null, R.style.text);
        nameView.setText(listName);
        nameView.setGravity(Gravity.CENTER_VERTICAL);
        nameView.setLayoutParams(nameLayoutParams);

        ImageButton editButton = new ImageButton(context);
        editButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_edit_icon));
        editButton.setBackgroundResource(R.drawable.item_background);
        ((GradientDrawable)editButton.getBackground()).setColor(ContextCompat.getColor(context, R.color.white));
        editButton.setColorFilter(ContextCompat.getColor(context, R.color.blue_500), android.graphics.PorterDuff.Mode.MULTIPLY);
        editButton.setLayoutParams(countLayoutParams);
        editButton.setOnClickListener(v -> activity.editList(listName));

        setOnClickListener(v -> activity.selectList(listName));

        addView(nameView);
        addView(editButton);
    }
}
