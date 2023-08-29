package com.example.todolist.ui;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.example.todolist.R;
import com.example.todolist.SwitchListActivity;
import com.example.todolist.data.ListNames;

public class ListView extends LinearLayout {

    public ListView(Context context, ListNames listData, SwitchListActivity activity) {
        super(context);
        setBackgroundResource(R.drawable.item_background);
        ((GradientDrawable)getBackground()).setColor(ContextCompat.getColor(context, R.color.blue_500));

        LayoutParams a = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        a.setMargins(10, 10, 10, 10);
        a.gravity = Gravity.CENTER;
        setLayoutParams(a);

        LayoutParams nameLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 100);
        nameLayoutParams.gravity = Gravity.CENTER;
        nameLayoutParams.setMargins(50, 10, 0, 10);
        LayoutParams countLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, 0);
        countLayoutParams.gravity = Gravity.CENTER;
        countLayoutParams.setMargins(0, 10, 0, 10);

        ContextThemeWrapper textThemeWrapper = new ContextThemeWrapper(context, R.style.text);
        TextView nameView = new TextView(textThemeWrapper, null, R.style.text);
        nameView.setText(listData.getListName());
        nameView.setEllipsize(TextUtils.TruncateAt.END);
        nameView.setMaxLines(1);
        nameView.setGravity(Gravity.CENTER_VERTICAL);
        nameView.setLayoutParams(nameLayoutParams);

        ImageButton editButton = new ImageButton(context);
        editButton.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_icon_settings));
        editButton.setBackgroundResource(R.drawable.item_background);
        ((GradientDrawable)editButton.getBackground()).setColor(ContextCompat.getColor(context, R.color.white));
        editButton.setColorFilter(ContextCompat.getColor(context, R.color.blue_500), android.graphics.PorterDuff.Mode.MULTIPLY);
        editButton.setLayoutParams(countLayoutParams);
        editButton.setOnClickListener(v -> activity.editList(listData));

        setOnClickListener(v -> activity.selectList(listData));

        addView(nameView);
        addView(editButton);
        setMinimumHeight(160);
    }
}
